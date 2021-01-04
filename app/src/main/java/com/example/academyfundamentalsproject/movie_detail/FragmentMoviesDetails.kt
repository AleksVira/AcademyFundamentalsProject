package com.example.academyfundamentalsproject.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.academyfundamentalsproject.R
import com.example.academyfundamentalsproject.data.MovieData
import com.example.academyfundamentalsproject.databinding.FragmentMovieDetailsBinding
import timber.log.Timber

class FragmentMoviesDetails : Fragment() {

    private var _viewBinding: FragmentMovieDetailsBinding? = null
    private val viewBinding get() = _viewBinding!!

    private lateinit var movieData: MovieData


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
        _viewBinding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            ageCategory.text = requireContext().getString(R.string.plus_sign, movieData.parentalRating.toString())
            movieName.text = movieData.movieName
            tagline.text = movieData.genre
            movieRating.setRating(movieData.ratingPercent)
            reviewsCounter.text = getString(R.string.reviews_counter, movieData.reviewsCount.toString())
            storylineTitle.text = requireContext().getString(R.string.storyline)
            storylineContent.text = movieData.movieDescription



            movieCast1.text = getString(R.string.tmp_name_1)
            movieCast2.text = getString(R.string.tmp_name_2)
            movieCast3.text = getString(R.string.tmp_name_3)
            movieCast4.text = getString(R.string.tmp_name_4)


/*
            itemMoviePicture.setBackgroundResource(movieData.moviePicturesResource)
*/


            backMarker.setOnClickListener { onBackPressed() }
            arrowBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun onBackPressed() {
        activity?.onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}
