package com.example.academyfundamentalsproject

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.academyfundamentalsproject.databinding.ActivityMainBinding
import com.example.academyfundamentalsproject.main_list.FragmentMoviesList
import com.example.academyfundamentalsproject.main_list.MovieCardClickListener
import com.example.academyfundamentalsproject.movie_detail.FragmentMoviesDetails
import com.example.academyfundamentalsproject.network.helpers.LoadingState
import com.example.academyfundamentalsproject.network.helpers.LoadingState.Companion.LOADED
import com.example.academyfundamentalsproject.network.helpers.LoadingState.Companion.LOADING
import com.example.academyfundamentalsproject.view_models.MoviesViewModel

class MainActivity : AppCompatActivity(), MovieCardClickListener {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MoviesViewModel by viewModels() {
        MoviesViewModelFactory(application,
            Injection.tmdbRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AcademyFundamentalsProject)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            getConfigFromApi()
        }

        viewModel.loadingState.observe(this) { event ->
            event.handle { state ->
                progressBarVisible(state)

            }


        }

        viewModel.apiConfig.observe(this) { config ->
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

    fun progressBarVisible(state: LoadingState) {
        when (state) {
            LOADING -> {
                binding.progressBar.isVisible = true
            }
            LOADED -> {
                binding.progressBar.isVisible = false
            }
        }
    }
}