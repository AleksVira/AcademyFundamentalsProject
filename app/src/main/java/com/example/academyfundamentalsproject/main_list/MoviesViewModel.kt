package com.example.academyfundamentalsproject.main_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.academyfundamentalsproject.data.MovieData
import com.example.academyfundamentalsproject.data.loadMovies
import kotlinx.coroutines.launch
import timber.log.Timber

class MoviesViewModel(app: Application) : AndroidViewModel(app) {

    private val _moviesDataList = MutableLiveData<List<MovieData>>()
    val moviesDataList: LiveData<List<MovieData>>
        get() = _moviesDataList


    private val _selectedMovie = MutableLiveData<MovieData>()
    val selectedMovie: LiveData<MovieData>
        get() = _selectedMovie

    fun select(item: Int) {
        _selectedMovie.value = _moviesDataList.value?.get(item)
    }

    init {
        viewModelScope.launch {
            _moviesDataList.value = loadMovies(context = getApplication())
        }
    }

    fun changeFavouriteState(adapterIndex: Int) {
        viewModelScope.launch {
            Timber.d("MyTAG_MainListViewModel_updateFavourite(): FAVOURITE state for $adapterIndex IS ->> ${
                _moviesDataList.value?.get(adapterIndex)?.isLiked
            }")
            _moviesDataList.value = fakeRequestChangeFavouriteState(adapterIndex)
            Timber.d("MyTAG_MainListViewModel_updateFavourite(): NEW FAVOURITE state for $adapterIndex IS ->> ${
                _moviesDataList.value?.get(adapterIndex)?.isLiked
            }")
        }
    }

    private suspend fun fakeRequestChangeFavouriteState(adapterIndex: Int): List<MovieData>? {
        val newList = _moviesDataList.value
        val listItem = newList?.get(adapterIndex)
        val likeState = listItem?.isLiked ?: false
        newList?.get(adapterIndex)?.isLiked = !likeState
        return newList
    }

}
