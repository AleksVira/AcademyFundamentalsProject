package com.example.academyfundamentalsproject.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.academyfundamentalsproject.common.ConsumableValue
import com.example.academyfundamentalsproject.data.loadFakeMovies
import com.example.academyfundamentalsproject.network.helpers.LoadingState
import com.example.academyfundamentalsproject.network.helpers.LoadingState.Companion.LOADED
import com.example.academyfundamentalsproject.network.helpers.LoadingState.Companion.LOADING
import com.example.academyfundamentalsproject.repositories.domain.Movie
import com.example.academyfundamentalsproject.repositories.domain.TmdbConfigData
import kotlinx.coroutines.*
import timber.log.Timber

class MoviesViewModel(
    private val app: Application,
    private val repository: TmdbRepository,
) : AndroidViewModel(app) {

    private var screenWidth: Int = 0

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

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

/*
    init {
        viewModelScope.launch {
            _moviesDataList.postValue(loadMovies(context = app))
        }
    }
*/

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
                    repository.getTmdbConfig()
                }

                delay(1000)
                val genresResponse = genresDeferredResponse.await()
                val configResponse = configDeferredResponse.await()
                Timber.d("MyTAG_MoviesViewModel_requestConfig(): $genresResponse, $configResponse")
                configResponse.genres = genresResponse
                _apiConfig.postValue(configResponse)
                _loadingState.value = ConsumableValue(LOADED)
            }
        }
    }

    fun loadFakeMovies() {
        viewModelScope.launch {
            _moviesDataList.postValue(loadFakeMovies(context = app))
        }
    }

    fun loadRealMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _loadingState.value = ConsumableValue(LOADING)
                _networkErrorState.value = ""
                delay(2000)
                val moviesResponse = withContext(Dispatchers.IO) {
                    repository.getNetworkTopRated()
                }
                Timber.d("MyTAG_MoviesViewModel_loadRealMovies(): $moviesResponse")
                setPicturesUrl(moviesResponse)

                // Для каждого фильма запрашиваю доп.данные (нужно узнать длительность)
                moviesResponse.forEach { movie: Movie ->
                    getMoreInfo(movie)
                }


                _moviesDataList.postValue(moviesResponse)
                _loadingState.value = ConsumableValue(LOADED)
            }
        }
    }

    private suspend fun getMoreInfo(movie: Movie) {
        withContext(Dispatchers.IO) {
            val additionalInfo = repository.getMovieInfo(movie.id)
            movie.movieLengthMinutes = additionalInfo.runtime
        }
    }

    private fun setPicturesUrl(
        moviesResponse: List<Movie>,
    ) {
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

    fun loadDurations(idList: List<Int>) {
        idList.forEach { movieId ->


        }

    }

}
