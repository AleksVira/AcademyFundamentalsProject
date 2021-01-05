package com.example.academyfundamentalsproject.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.academyfundamentalsproject.R
import com.example.academyfundamentalsproject.data.MovieData
import com.example.academyfundamentalsproject.databinding.FragmentMovieDetailsBinding
import com.example.academyfundamentalsproject.utils.ActorsListSpaceDecorator
import timber.log.Timber

class FragmentMoviesDetails : Fragment() {

    private var _detailBinding: FragmentMovieDetailsBinding? = null
    private val detailBinding get() = _detailBinding!!

    private lateinit var movieData: MovieData
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieData = it.getParcelable(PARAM_MOVIE_DATA)!!
            Timber.d("MyTAG_FragmentMoviesDetails_onCreate(): PARAM = ${movieData}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _detailBinding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(detailBinding) {
            ageCategory.text =
                requireContext().getString(R.string.plus_sign, movieData.parentalRating.toString())
            movieName.text = movieData.movieName
            tagline.text = movieData.genre
            movieRating.setRating(movieData.ratingPercent)
            reviewsCounter.text =
                getString(R.string.reviews_counter, movieData.reviewsCount.toString())
            storylineTitle.text = requireContext().getString(R.string.storyline)
            storylineContent.text = movieData.movieDescription

            initActorsList()
            actorsListAdapter.actors = movieData.actorsList

            backMarker.setOnClickListener { onBackPressed() }
            arrowBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun initActorsList() {

        val recycler: RecyclerView = detailBinding.actorsList
        actorsListAdapter = ActorsListAdapter()
        recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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
