package com.example.academyfundamentalsproject.main_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.academyfundamentalsproject.R
import com.example.academyfundamentalsproject.databinding.FragmentMoviesListBinding
import com.example.academyfundamentalsproject.view_models.MoviesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class FragmentMoviesList : Fragment() {

    private val moviesViewModel by activityViewModels<MoviesViewModel>()

    private lateinit var movieCardClickListener: MovieCardClickListener
    private var mainListAdapter = MovieListPagedAdapter(
        movieCardClickListener = { movie ->
            moviesViewModel.select(movie)
            movieCardClickListener.onMovieCardSelected()
        },
        onFavoriteClick = { movie, absPosition  ->
            Timber.d("MyTAG_FragmentMoviesList_(): ${movie.id}, $absPosition")
            moviesViewModel.changeFavouriteState(movie, absPosition)
        })

    private lateinit var binding: FragmentMoviesListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        moviesViewModel.updatedMovie.observe(viewLifecycleOwner) { event ->
            event?.handle { movie ->
                mainListAdapter.updateMovieTime(movie)
            }
        }

        moviesViewModel.updatedFavouriteMovie.observe(viewLifecycleOwner) { event ->
            event?.handle { it ->
                mainListAdapter.updateFavouriteState(it.first, it.second)
            }
        }
    }

    private fun initView() {
        binding.listMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = mainListAdapter
            addItemDecoration(MovieGridSpaceDecorator(space = resources.getDimensionPixelSize(R.dimen.movie_grid_spacing)))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieCardClickListener) {
            movieCardClickListener = context
        }
    }

    private fun fetchMovies() {
        lifecycleScope.launch {
            moviesViewModel.loadPagedMovies().collectLatest { pagingData ->
                mainListAdapter.submitData(pagingData)
            }
        }
    }

    interface MovieCardClickListener {
        fun onMovieCardSelected()
    }
}