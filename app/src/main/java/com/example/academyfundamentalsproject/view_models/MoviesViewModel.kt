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
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MoviesViewModel(
    private val repository: TmdbRepository,
) : ViewModel() {

    private var screenWidth: Int = 0

    private var currentMovieResult: Flow<PagingData<Movie>>? = null

    private val _loadingState = MutableLiveData<ConsumableValue<LoadingState>>()
    val loadingState: LiveData<ConsumableValue<LoadingState>>
        get() = _loadingState

    private val _networkErrorState = MutableLiveData<String>()
    val networkErrorState: LiveData<String>
        get() = _networkErrorState

    private val _apiConfig = MutableLiveData<TmdbConfigData>()
    val apiConfig: LiveData<TmdbConfigData>
        get() = _apiConfig

    private val _moviesDataList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = _moviesDataList

    private val _actorsDataList = MutableLiveData<List<Actor>>()
    val actorsDataList: LiveData<List<Actor>>
        get() = _actorsDataList

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    private val _updatedMovie = MutableLiveData<Int>()
    val updatedMovie: LiveData<Int>
        get() = _updatedMovie



    fun select(movieId: Int) {
        _selectedMovie.postValue(_moviesDataList.value?.find { movie -> movie.id == movieId })
    }

    fun changeFavouriteState(movieId: Int) {
        viewModelScope.launch {
            _moviesDataList.postValue(fakeRequestChangeFavouriteState(movieId))
        }
    }

    private fun fakeRequestChangeFavouriteState(movieId: Int): List<Movie>? {
        val oldList = _moviesDataList.value
        val oldItem: Movie? = oldList?.find { movie -> movie.id == movieId }

        oldItem?.let {
            val movieIndex = oldList.indexOf(oldItem)
            val likeState = oldItem.isLiked
            val newItem = oldItem.copy(isLiked = !likeState)
            val newList = oldList.toMutableList()
            newList[movieIndex] = newItem
            return newList
        } ?: run {
            return oldList
        }
    }

    fun requestConfig() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _loadingState.value = ConsumableValue(LOADING)
                _networkErrorState.value = ""

                val genresDeferredResponse = async(Dispatchers.IO) {
                    repository.getGenres()
                }
                val configDeferredResponse = async(Dispatchers.IO) {
                    when (val networkResponse = repository.getTmdbConfig()) {
                        is Resource.Success -> {
                            networkResponse.value
                        }
                        is Resource.Failure<*> -> {
                            Throwable("MESSAGE")
                            errorHandler(networkResponse.error)
                        }
                        is Resource.Failure.HttpError -> TODO()
                        is Resource.Success.Empty -> TODO()
                    }
                }

//                delay(1000)
                try {
                    val genresResponse = genresDeferredResponse.await()
                    val configResponse = configDeferredResponse.await()
                    Timber.d("MyTAG_MoviesViewModel_requestConfig(): $genresResponse, $configResponse")
//                    yield()
                    _apiConfig.postValue(configResponse as TmdbConfigData?)
                    _loadingState.value = ConsumableValue(LOADED)
                } catch (e: Exception) {
                    Timber.d("MyTAG_MoviesViewModel_requestConfig(): CATCH ERROR ? ${e.message}")
                } finally {
                    Timber.d("MyTAG_MoviesViewModel_requestConfig(): FINALLY")
                }
            }
        }
    }

    private fun errorHandler(error: Throwable?) {
        Timber.d("MyTAG_MoviesViewModel_errorHandler(): $error")

    }

/*    fun loadRealMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _loadingState.value = ConsumableValue(LOADING)
                _networkErrorState.value = ""
//                delay(2000)
                val moviesResponse = withContext(Dispatchers.IO) {
                    repository.getNetworkTopRated()
                }
                Timber.d("MyTAG_MoviesViewModel_loadRealMovies(): $moviesResponse")
                withContext(Dispatchers.Default) {
                    setPicturesUrl(moviesResponse)
                }
                _moviesDataList.postValue(moviesResponse)
                _loadingState.value = ConsumableValue(LOADED)
                // Для каждого фильма запрашиваю доп.данные (нужно узнать длительность)
                loadSingleDurations(moviesResponse)
            }
        }
    }*/


    fun loadPagedMovies(): Flow<PagingData<Movie>> {
        val posterSizeParameter = selectImageWidth(apiConfig.value?.posterSizes, screenWidth / 2)
        val backdropImageSizeParameter = selectImageWidth(apiConfig.value?.backdropSizes, screenWidth)

        val lastResult = currentMovieResult
        if (lastResult != null) {
            return lastResult
        }

        val newResult: Flow<PagingData<Movie>> = repository.getPagedNetworkTopRated()
            .map { pagingData ->
                Timber.d("MyTAG_MoviesViewModel_loadPagedMovies(): ${pagingData}")
                pagingData.map { currentMovie ->

//                    loadSingleDurations(currentMovie)

                    currentMovie.copy(
                        posterUrl = apiConfig.value?.secureBaseUrl + posterSizeParameter + currentMovie.posterUrl,
                        backdropImageUrl = apiConfig.value?.secureBaseUrl + backdropImageSizeParameter + currentMovie.backdropImageUrl,
                    )
                }
            }
            .cachedIn(viewModelScope)
        currentMovieResult = newResult
        return newResult
    }

/*    private fun setPicturesUrl(moviesResponse: List<Movie>) {
        val posterSizeParameter = selectImageWidth(apiConfig.value?.posterSizes, screenWidth / 2)
        val backdropImageSizeParameter = selectImageWidth(apiConfig.value?.backdropSizes, screenWidth)

        moviesResponse.forEach { movie ->
            val posterTmpUrl =
                apiConfig.value?.secureBaseUrl + posterSizeParameter + movie.posterUrl
            movie.posterUrl = posterTmpUrl
            val backdropImageTmpUrl =
                apiConfig.value?.secureBaseUrl + backdropImageSizeParameter + movie.backdropImageUrl
            movie.backdropImageUrl = backdropImageTmpUrl
        }
    }*/

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


    private fun loadSingleDurations(moviesList: List<Movie>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                moviesList.forEachIndexed() { index, movie ->
//                    delay(1000)
                    val additionalInfo = repository.getMovieInfo(movie.id)
                    moviesList[index].movieLengthMinutes = additionalInfo.runtime
                    withContext(Dispatchers.Main) {
                        _updatedMovie.value = index
                    }
                }
            }
        }
    }

/*
    private fun loadOneMovieDurations(moviesList: List<Movie>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                moviesList.forEachIndexed() { index, movie ->
//                    delay(1000)
                    val additionalInfo = repository.getMovieInfo(movie.id)
                    moviesList[index].movieLengthMinutes = additionalInfo.runtime
                    withContext(Dispatchers.Main) {
                        _updatedMovie.value = index
                    }
                }
            }
        }
    }
*/

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
        val avatarSizeParameter = selectImageWidth(apiConfig.value?.avatarSizes, screenWidth / 4)
        actorsResponse?.forEach { actor ->
            if (actor.imageUrl.isEmpty()) {
                return
            }
            val avatarTmpUrl =
                apiConfig.value?.secureBaseUrl + avatarSizeParameter + actor.imageUrl
            actor.imageUrl = avatarTmpUrl
        }
    }

    fun clearActors() {
        _actorsDataList.postValue(emptyList())
    }

}