package com.example.academyfundamentalsproject.main_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.academyfundamentalsproject.R
import com.example.academyfundamentalsproject.data.loadMovies
import com.example.academyfundamentalsproject.databinding.FragmentMoviesListBinding
import com.example.academyfundamentalsproject.utils.MovieCardClickListener
import com.example.academyfundamentalsproject.utils.MovieGridSpaceDecorator
import kotlinx.coroutines.launch
import timber.log.Timber

class FragmentMoviesList : Fragment() {

    private var movieCardClickListener: MovieCardClickListener? = null
    private lateinit var recycler: RecyclerView
    private lateinit var mainListAdapter: MainListAdapter

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)

/*
        initView()
        viewLifecycleOwner.lifecycleScope.launch {
            mainListAdapter.movies = loadMovies(requireContext())
            Timber.d("MyTAG_FragmentMoviesList_onViewCreated(): ANSWER! ")
        }
*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewLifecycleOwner.lifecycleScope.launch {
            mainListAdapter.movies = loadMovies(requireContext())
            Timber.d("MyTAG_FragmentMoviesList_onViewCreated(): ANSWER! ")
        }

    }

    private fun initView() {
        binding.moviesListHeader.text = getString(R.string.movies_list)
        recycler = binding.moviesList
        mainListAdapter = MainListAdapter({ adapterIndex ->
            Timber.d("MyTAG_FragmentMoviesList_initView(): $adapterIndex, ${mainListAdapter.movies[adapterIndex].movieName} CARD clicked")
            movieCardClickListener?.onMovieCardClicked(mainListAdapter.movies[adapterIndex])
        }, { adapterIndex: Int, view: View ->
            Timber.d("MyTAG_FragmentMoviesList_initView(): ${mainListAdapter.movies[adapterIndex].movieName} clicked from ${view.javaClass.canonicalName}")
        })
        mainListAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        recycler.adapter = mainListAdapter
        recycler.addItemDecoration(MovieGridSpaceDecorator(space = resources.getDimensionPixelSize(
            R.dimen.movie_grid_spacing)))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MovieCardClickListener) {
            movieCardClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        movieCardClickListener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}