package com.example.academyfundamentalsproject.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.academyfundamentalsproject.R
import com.example.academyfundamentalsproject.data.MovieData
import com.example.academyfundamentalsproject.databinding.FragmentMovieDetailsBinding
import com.example.academyfundamentalsproject.main_list.MoviesViewModel
import com.example.academyfundamentalsproject.utils.ActorsListSpaceDecorator

class FragmentMoviesDetails : Fragment() {

    private val moviesViewModel by activityViewModels<MoviesViewModel>()

    private var _detailBinding: FragmentMovieDetailsBinding? = null
    private val detailBinding get() = _detailBinding!!

    private lateinit var actorsListAdapter: ActorsListAdapter

    companion object {
        private const val PARAM_MOVIE_DATA = "PARAM_MOVIE_DATA"

        fun newInstance(movieData: MovieData): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val args = Bundle()
            args.putParcelable(PARAM_MOVIE_DATA, movieData)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _detailBinding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesViewModel.selectedMovie.observe(viewLifecycleOwner, { movieData ->
            bindDetailMovie(movieData)
        })

        detailBinding.backMarker.setOnClickListener { onBackPressed() }
        detailBinding.arrowBack.setOnClickListener { onBackPressed() }
    }

    private fun bindDetailMovie(movieData: MovieData) {
        val genreString = movieData.genresList.joinToString { genre -> genre.name }

        with(detailBinding) {

            Glide.with(requireContext())
                .load(movieData.detailImageUrl)
                .placeholder(R.drawable.vertical_background)
                .fallback(R.drawable.vertical_background)
                .into(detailBackdrop)

            ageCategory.text =
                requireContext().getString(R.string.plus_sign, movieData.pgAge.toString())
            movieName.text = movieData.movieName
            tagline.text = genreString
            movieRating.setRating(movieData.ratingPercent)
            reviewsCounter.text =
                getString(R.string.reviews_counter, movieData.reviewsCount.toString())
            storylineTitle.text =
                requireContext().getString(R.string.storyline)
            storylineContent.text = movieData.storyLine

            if (movieData.actorsList.isNotEmpty()) {
                castTitle.isVisible = true
                initActorsList()
                actorsListAdapter.actors = movieData.actorsList
            } else {
                castTitle.isVisible = false
            }
        }
    }

    private fun initActorsList() {
        val recycler: RecyclerView = detailBinding.actorsList
        actorsListAdapter = ActorsListAdapter()
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = actorsListAdapter
        recycler.addItemDecoration(ActorsListSpaceDecorator(space = resources.getDimensionPixelSize(
            R.dimen.actors_list_spacing)))
    }

    private fun onBackPressed() {
        activity?.onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _detailBinding = null
    }
}
