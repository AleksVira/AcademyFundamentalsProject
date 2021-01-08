package com.example.academyfundamentalsproject.main_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.academyfundamentalsproject.R
import com.example.academyfundamentalsproject.databinding.FragmentMoviesListBinding
import com.example.academyfundamentalsproject.utils.MovieCardClickListener
import com.example.academyfundamentalsproject.utils.MovieGridSpaceDecorator
import timber.log.Timber

class FragmentMoviesList : Fragment() {

    private val mainListViewModel by activityViewModels<MainListViewModel>()

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        mainListViewModel.moviesDataList.observe(viewLifecycleOwner, Observer { movieList ->
            mainListAdapter.movies = movieList
        })
    }

    private fun initView() {
        binding.moviesListHeader.text = getString(R.string.movies_list)
        recycler = binding.moviesList

        mainListAdapter = MainListAdapter({ adapterIndex ->
            Timber.d("MyTAG_FragmentMoviesList_initView(): $adapterIndex, ${mainListAdapter.movies[adapterIndex].movieName} CARD clicked")
            movieCardClickListener?.onMovieCardClicked(mainListAdapter.movies[adapterIndex])
        }, { adapterIndex: Int, _: View ->
            mainListViewModel.changeFavouriteState(adapterIndex)
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