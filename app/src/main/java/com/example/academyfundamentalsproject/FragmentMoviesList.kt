package com.example.academyfundamentalsproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.academyfundamentalsproject.databinding.FragmentMoviesListBinding

class FragmentMoviesList : Fragment() {

    private var movieCardClickListener: MovieCardClickListener? = null
    private lateinit var mainListAdapter: MainListAdapter


    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)

        binding.root.setOnClickListener{
            movieCardClickListener?.onMovieCardClicked()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        mainListAdapter.movies = MovieMockedData().getMovieData()
    }

    private fun initView() {
        binding.moviesListHeader.text = "Movies list"
        val recycler: RecyclerView = binding.moviesList
        mainListAdapter = MainListAdapter()
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        recycler.adapter = mainListAdapter
        recycler.addItemDecoration(MovieGridSpaceDecorator(spaceInPx = resources.getDimensionPixelSize(R.dimen.movie_grid_spacing)))
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