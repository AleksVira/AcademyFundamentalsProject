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
import com.example.academyfundamentalsproject.data.Movie
import com.example.academyfundamentalsproject.databinding.FragmentMovieDetailsBinding
import com.example.academyfundamentalsproject.main_list.MoviesViewModel
import com.example.academyfundamentalsproject.utils.ActorsListSpaceDecorator

class FragmentMoviesDetails : Fragment() {

    private val moviesViewModel by activityViewModels<MoviesViewModel>()

    private lateinit var detailBinding: FragmentMovieDetailsBinding

    private lateinit var actorsListAdapter: ActorsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        detailBinding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesViewModel.selectedMovie.observe(viewLifecycleOwner, { movieData ->
            bindDetailMovie(movieData)
        })

        detailBinding.backMarker.setOnClickListener { onBackPressed() }
    }

    private fun bindDetailMovie(movie: Movie) {
        val genreString = movie.genresList.joinToString { genre -> genre.name }

        with(detailBinding) {

            Glide.with(requireContext())
                .load(movie.detailImageUrl)
                .placeholder(R.drawable.vertical_background)
                .fallback(R.drawable.vertical_background)
                .into(detailBackdrop)

            ageCategory.text = getString(R.string.plus_sign, movie.pgAge)
            movieName.text = movie.movieName
            tagline.text = genreString
            movieRating.setRating(movie.ratingPercent)
            reviewsCounter.text = getString(R.string.reviews_counter, movie.reviewsCount)
            storylineTitle.text = getString(R.string.storyline)
            storylineContent.text = movie.storyLine

            if (movie.actorsList.isNotEmpty()) {
                castTitle.isVisible = true
                actorsListAdapter = ActorsListAdapter(movie.actorsList)
                initActorsList()
            } else {
                castTitle.isVisible = false
            }
        }
    }

    private fun initActorsList() {
        detailBinding.actorsList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = actorsListAdapter
            addItemDecoration(ActorsListSpaceDecorator(space = resources.getDimensionPixelSize(R.dimen.actors_list_spacing)))
        }
    }

    private fun onBackPressed() {
        activity?.onBackPressed()
    }

}