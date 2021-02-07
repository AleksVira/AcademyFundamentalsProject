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
import com.example.academyfundamentalsproject.main_list.MovieItemDiffCallback.Companion.RUNTIME
import com.example.academyfundamentalsproject.view_models.MoviesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class FragmentMoviesList : Fragment() {

    private val moviesViewModel by activityViewModels<MoviesViewModel>()

    private lateinit var movieCardClickListener: MovieCardClickListener
    private var mainListAdapter = MovieListPagedAdapter(
        movieCardClickListener = { movieId ->
            moviesViewModel.select(movieId)
            movieCardClickListener.onMovieCardSelected()
        },
        onFavoriteClick = { movieId, absPosition  ->
            Timber.d("MyTAG_FragmentMoviesList_(): $movieId, $absPosition")
            moviesViewModel.changeFavouriteState(movieId)
        })

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchMovies()
//        moviesViewModel.loadRealMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
//        moviesViewModel.moviesList.observe(viewLifecycleOwner) { movieList ->
//            mainListAdapter.submitList(movieList)
//        }

        moviesViewModel.updatedMovie.observe(viewLifecycleOwner) { index ->
            val bundle = Bundle()
            bundle.putInt(RUNTIME, moviesViewModel.moviesList.value?.get(index)?.movieLengthMinutes ?: 0)
            mainListAdapter.notifyItemChanged(index, bundle)
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

    override fun onDestroyView() {
        super.onDestroyView()
        moviesViewModel.clearActors()
        _binding = null
    }

    private fun fetchMovies() {
        lifecycleScope.launch {
            moviesViewModel.loadPagedMovies().collectLatest { pagingData ->
                mainListAdapter.submitData(pagingData)
            }
        }
    }

}