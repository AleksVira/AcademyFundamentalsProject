package com.example.academyfundamentalsproject.main_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.academyfundamentalsproject.R
import com.example.academyfundamentalsproject.databinding.FragmentMoviesListBinding
import com.example.academyfundamentalsproject.utils.MovieCardClickListener
import com.example.academyfundamentalsproject.utils.MovieGridSpaceDecorator

class FragmentMoviesList : Fragment() {

    private val moviesViewModel by activityViewModels<MoviesViewModel>()

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
        moviesViewModel.moviesDataList.observe(viewLifecycleOwner, { movieList ->
            mainListAdapter.submitList(movieList.toMutableList())
        })
    }

    private fun initView() {
        binding.moviesListHeader.text = getString(R.string.movies_list)
        recycler = binding.moviesList

        mainListAdapter = MainListAdapter({ adapterIndex ->
            moviesViewModel.select(adapterIndex)
            movieCardClickListener?.onMovieCardSelected()
        }, { adapterIndex: Int, _: View ->
            moviesViewModel.changeFavouriteState(adapterIndex)
        })
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