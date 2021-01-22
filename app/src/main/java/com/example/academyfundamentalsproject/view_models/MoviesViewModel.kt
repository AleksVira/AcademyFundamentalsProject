package com.example.academyfundamentalsproject.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.academyfundamentalsproject.common.ConsumableValue
import com.example.academyfundamentalsproject.repositories.domain.Movie
import com.example.academyfundamentalsproject.data.loadFakeMovies
import com.example.academyfundamentalsproject.network.helpers.LoadingState
import com.example.academyfundamentalsproject.network.helpers.LoadingState.Companion.LOADED
import com.example.academyfundamentalsproject.network.helpers.LoadingState.Companion.LOADING
import com.example.academyfundamentalsproject.repositories.domain.TmdbConfigData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MoviesViewModel(
    private val app: Application,
    private val repository: TmdbRepository,
) : AndroidViewModel(app) {

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
                withContext(Dispatchers.IO) {
                    delay(4000)
                    val configResponse = repository.getTmdbConfig()
                    Timber.d("MyTAG_MoviesViewModel_requestConfig(): $configResponse")
                    _apiConfig.postValue(configResponse)
                }
                _loadingState.value = ConsumableValue(LOADED)
            }
        }
    }

    fun loadFakeMovies() {
        viewModelScope.launch {
            _moviesDataList.postValue(loadFakeMovies(context = app))
        }
    }

//    private fun getTmdbApiConfig(): ApiConfig {
//
//    }


}

