package com.example.academyfundamentalsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.academyfundamentalsproject.main_list.FragmentMoviesList
import com.example.academyfundamentalsproject.movie_detail.FragmentMoviesDetails
import com.example.academyfundamentalsproject.main_list.MovieCardClickListener
import com.example.academyfundamentalsproject.view_model.MoviesViewModel

class MainActivity : AppCompatActivity(), MovieCardClickListener {

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AcademyFundamentalsProject)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            getConfigFromApi()
            startMovieList()
        }
    }

    private fun getConfigFromApi() {
        viewModel.requestConfig()
    }

    private fun startMovieList() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, FragmentMoviesList())
            .commit()
    }

    override fun onMovieCardSelected() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, FragmentMoviesDetails())
            .addToBackStack(FragmentMoviesDetails::class.java.name)
            .commit()
    }
}