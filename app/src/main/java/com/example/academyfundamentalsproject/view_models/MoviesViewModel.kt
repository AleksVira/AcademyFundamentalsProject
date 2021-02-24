package com.example.academyfundamentalsproject.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.academyfundamentalsproject.common.ConsumableValue
import com.example.academyfundamentalsproject.data.Actor
import com.example.academyfundamentalsproject.network.Resource
import com.example.academyfundamentalsproject.network.helpers.LoadingState
import com.example.academyfundamentalsproject.network.helpers.LoadingState.Companion.LOADED
import com.example.academyfundamentalsproject.network.helpers.LoadingState.Companion.LOADING
import com.example.academyfundamentalsproject.repositories.TmdbRepository
import com.example.academyfundamentalsproject.repositories.domain.Movie
import com.example.academyfundamentalsproject.repositories.domain.TmdbConfigData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MoviesViewModel(
    private val repository: TmdbRepository,
) : ViewModel() {

    private var screenWidth: Int = 0
    lateinit var apiConfig: TmdbConfigData

    private var currentMovieResult: Flow<PagingData<Movie>>? = null

    private val _loadingState = MutableLiveData<ConsumableValue<LoadingState>>()
    val loadingState: LiveData<ConsumableValue<LoadingState>>
        get() = _loadingState

    private val _networkErrorState = MutableLiveData<String>()
    val networkErrorState: LiveData<String>
        get() = _networkErrorState

    private val _startMovieList = MutableLiveData<ConsumableValue<Boolean>>()
    val startMovieList: LiveData<ConsumableValue<Boolean>>
        get() = _startMovieList

    private val _updatedFavouriteMovie = MutableLiveData<ConsumableValue<Pair<Int, Movie>>>()
    val updatedFavouriteMovie: LiveData<ConsumableValue<Pair<Int, Movie>>>
        get() = _updatedFavouriteMovie

    private val _actorsDataList = MutableLiveData<List<Actor>>()
    val actorsDataList: LiveData<List<Actor>>
        get() = _actorsDataList

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    private val _updatedMovie = MutableLiveData<ConsumableValue<Movie>>()
    val updatedMovie: LiveData<ConsumableValue<Movie>>
        get() = _updatedMovie

    fun select(movie: Movie) {
        _selectedMovie.postValue(movie)
    }

    fun changeFavouriteState(movie: Movie, absPosition: Int) {
        viewModelScope.launch {
            _updatedFavouriteMovie.postValue(ConsumableValue(Pair(absPosition, fakeRequestChangeFavouriteState(movie))))
        }
    }

    private fun fakeRequestChangeFavouriteState(movie: Movie): Movie {
        return movie.copy(isLiked = !(movie.isLiked))
    }

    fun requestConfig() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _loadingState.value = ConsumableValue(LOADING)
                _networkErrorState.value = ""

                val jobGenresResponse = launch(Dispatchers.IO) {
                    repository.getGenres()
                }
                val jobConfigResponse = launch(Dispatchers.IO) {
                    when (val networkResponse = repository.getTmdbConfig()) {
                        is Resource.Success -> {
                            Timber.d("MyTAG_MoviesViewModel_requestConfig(): ${networkResponse.value}")
                            apiConfig = networkResponse.value
                        }
                        is Resource.Failure<*> -> {
                            Throwable("MESSAGE")
                        }
                        is Resource.Failure.HttpError -> TODO()
                        is Resource.Success.Empty -> TODO()
                    }
                }
                jobGenresResponse.join()
                jobConfigResponse.join()
                withContext(Dispatchers.Main) {
                    _loadingState.value = ConsumableValue(LOADED)
                    _startMovieList.value = ConsumableValue(true)
                }
            }
        }
    }

    fun loadPagedMovies(): Flow<PagingData<Movie>> {
        val posterSizeParameter = selectImageWidth(apiConfig.posterSizes, screenWidth / 2)
        val backdropImageSizeParameter = selectImageWidth(apiConfig.backdropSizes, screenWidth)

        val lastResult = currentMovieResult
        if (lastResult != null) {
            return lastResult
        }

        val newResult: Flow<PagingData<Movie>> = repository.getPagedNetworkTopRated()
            .map { pagingData ->
                Timber.d("MyTAG_MoviesViewModel_loadPagedMovies(): ${pagingData}")
                pagingData.map { currentMovie ->

                    updateMovieDuration(currentMovie)

                    currentMovie.copy(
                        posterUrl = apiConfig.secureBaseUrl + posterSizeParameter + currentMovie.posterUrl,
                        backdropImageUrl = apiConfig.secureBaseUrl + backdropImageSizeParameter + currentMovie.backdropImageUrl,
                    )
                }
            }
            .cachedIn(viewModelScope)
        currentMovieResult = newResult
        return newResult
    }

    private fun selectImageWidth(posterSizes: List<String>?, widthLimit: Int): String {
        val reversedSizes = posterSizes?.asReversed()
        reversedSizes?.forEach { stringValue ->
            val res = stringValue.replace("[^0-9]".toRegex(), "")
            if (res.isNotEmpty()) {
                val resultNumber = res.toInt()
                if (resultNumber < widthLimit) {
                    return stringValue
                }
            }
        }
        return "original"
    }

    fun saveScreenWidth(deviceWidth: Int) {
        Timber.d("MyTAG_MoviesViewModel_saveScreenWidth(): REAL WIDTH = $deviceWidth")
        screenWidth = deviceWidth
    }

    private fun updateMovieDuration(movie: Movie) {
        Timber.d("MyTAG_MoviesViewModel_updateMovieDuration(): START UPDATE")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val additionalInfo = repository.getMovieInfo(movie.id)) {
                    is Resource.Success.Value -> {
                        val resultMovie: Movie = movie.copy(movieLengthMinutes = additionalInfo.value.runtime)
                        Timber.d("MyTAG_MoviesViewModel_loadSingleDurations(): ${movie.movieName} -> ${additionalInfo.value.runtime}")
                        withContext(Dispatchers.Main) {
                            _updatedMovie.value = ConsumableValue(resultMovie)
                        }
                    }

                    is Resource.Failure.Error -> {
                        Timber.e("MyTAG_MoviesViewModel_loadSingleDurations(): ${additionalInfo.error.message}, ${additionalInfo.error.stackTrace}")

                        Resource.Failure.Error(Throwable(additionalInfo.error.message))
                    }
                    is Resource.Failure.HttpError -> TODO()
                    Resource.Success.Empty -> TODO()
                    is Resource.Success.HttpResponseImpl -> TODO()
                }
            }
        }
    }

    fun loadActorsFromNetwork(movie: Movie) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _loadingState.value = ConsumableValue(LOADING)
                _networkErrorState.value = ""

                withContext(Dispatchers.IO) {
                    val actorsResponse = repository.getActors(movie.id)
                    withContext(Dispatchers.Default) {
                        setAvatarUrl(actorsResponse)
                    }
                    _actorsDataList.postValue(actorsResponse)
                }
            }
            _loadingState.value = ConsumableValue(LOADED)
        }
    }

    private fun setAvatarUrl(actorsResponse: List<Actor>?) {
        val avatarSizeParameter = selectImageWidth(apiConfig.avatarSizes, screenWidth / 4)
        actorsResponse?.forEach { actor ->
            if (actor.imageUrl.isEmpty()) {
                return
            }
            val avatarTmpUrl =
                apiConfig.secureBaseUrl + avatarSizeParameter + actor.imageUrl
            actor.imageUrl = avatarTmpUrl
        }
    }

    fun clearActors() {
        _actorsDataList.postValue(emptyList())
    }

}