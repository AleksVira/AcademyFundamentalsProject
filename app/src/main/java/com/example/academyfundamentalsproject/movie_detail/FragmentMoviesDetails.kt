package com.example.academyfundamentalsproject.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.academyfundamentalsproject.R
import com.example.academyfundamentalsproject.common.ActorsListSpaceDecorator
import com.example.academyfundamentalsproject.data.Actor
import com.example.academyfundamentalsproject.databinding.FragmentMovieDetailsBinding
import com.example.academyfundamentalsproject.repositories.domain.Movie
import com.example.academyfundamentalsproject.view_models.MoviesViewModel

class FragmentMoviesDetails : Fragment() {

    private val moviesViewModel by activityViewModels<MoviesViewModel>()

    private var _detailBinding: FragmentMovieDetailsBinding? = null
    private val detailBinding get() = _detailBinding!!

    private lateinit var actorsListAdapter: ActorsListAdapter

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
            moviesViewModel.loadActorsFromNetwork(movieData)
            bindDetailMovie(movieData)
        })
        moviesViewModel.actorsDataList.observe(viewLifecycleOwner) { actorsList ->
            showActors(actorsList)
        }

        detailBinding.tvBackMarker.setOnClickListener { onBackPressed() }

    }

    private fun bindDetailMovie(movie: Movie) {
        val genreString = movie.genresList.joinToString()
        with(detailBinding) {

            tvCastTitle.isVisible = false
            listActors.isVisible = false

            Glide.with(requireContext())
                .load(movie.backdropImageUrl)
                .placeholder(R.drawable.vertical_background)
                .fallback(R.drawable.vertical_background)
                .into(ivDetailBackdrop)

            tvAgeCategory.text = getString(R.string.plus_sign, movie.pgAge)
            tvMovieName.text = movie.movieName
            tvTagLine.text = genreString
            customMovieRating.setRating(movie.ratingPercent)
            tvReviewsCounter.text = getString(R.string.reviews_counter, movie.reviewsCount)
            tvStorylineTitle.text = getString(R.string.storyline)
            tvStorylineContent.text = movie.storyLine

        }
    }

    private fun showActors(actorsList: List<Actor>?) {
        if (actorsList?.isNotEmpty() == true) {
            detailBinding.listActors.isVisible = true
            detailBinding.tvCastTitle.isVisible = true
            actorsListAdapter = ActorsListAdapter(actorsList)
            detailBinding.listActors.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = actorsListAdapter
                addItemDecoration(ActorsListSpaceDecorator(space = resources.getDimensionPixelSize(R.dimen.actors_list_spacing)))
            }
        } else {
            detailBinding.tvCastTitle.isVisible = false
        }
    }

    private fun onBackPressed() {
        activity?.onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        moviesViewModel.clearActors()
        _detailBinding = null
    }

}