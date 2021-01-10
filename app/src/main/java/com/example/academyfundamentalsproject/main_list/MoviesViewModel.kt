package com.example.academyfundamentalsproject.main_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.academyfundamentalsproject.data.Movie
import com.example.academyfundamentalsproject.data.loadMovies
import kotlinx.coroutines.launch

class MoviesViewModel(app: Application) : AndroidViewModel(app) {

    private val _moviesDataList = MutableLiveData<List<Movie>>()
    val moviesList: LiveData<List<Movie>>
        get() = _moviesDataList

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    init {
        viewModelScope.launch {
            _moviesDataList.value = loadMovies(context = getApplication())
        }
    }

    fun select(movieId: Int) {
        _selectedMovie.postValue(_moviesDataList.value?.find { movie -> movie.id == movieId })
    }

    fun changeFavouriteState(movieId: Int) {
        viewModelScope.launch {
            _moviesDataList.value = fakeRequestChangeFavouriteState(movieId)
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
        } ?: run{
            return oldList
        }
    }


}
