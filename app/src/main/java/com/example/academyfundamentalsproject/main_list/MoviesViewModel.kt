package com.example.academyfundamentalsproject.main_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.academyfundamentalsproject.data.MovieData
import com.example.academyfundamentalsproject.data.loadMovies
import kotlinx.coroutines.launch

class MoviesViewModel(app: Application) : AndroidViewModel(app) {

    private val _moviesDataList = MutableLiveData<List<MovieData>>()
    val moviesDataList: LiveData<List<MovieData>>
        get() = _moviesDataList

    private val _selectedMovie = MutableLiveData<MovieData>()
    val selectedMovie: LiveData<MovieData>
        get() = _selectedMovie

    init {
        viewModelScope.launch {
            _moviesDataList.value = loadMovies(context = getApplication())
        }
    }

    fun select(item: Int) {
        _selectedMovie.value = _moviesDataList.value?.get(item)
    }

    fun changeFavouriteState(adapterIndex: Int) {
        viewModelScope.launch {
            _moviesDataList.value = fakeRequestChangeFavouriteState(adapterIndex)
        }
    }

    private suspend fun fakeRequestChangeFavouriteState(adapterIndex: Int): List<MovieData>? {
        val oldList = _moviesDataList.value
        val oldItem: MovieData = oldList?.get(adapterIndex)!!
        val likeState = oldItem.isLiked
        val newItem = oldItem.copy(isLiked = !likeState)
        val newList = oldList.toMutableList()
        newList[adapterIndex] = newItem
        return newList
    }


}
