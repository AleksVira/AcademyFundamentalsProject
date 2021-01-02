package com.example.academyfundamentalsproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.academyfundamentalsproject.databinding.FragmentMovieDetailsBinding

class FragmentMoviesDetails : Fragment() {

    private var _viewBinding: FragmentMovieDetailsBinding? = null
    private val viewBinding get() = _viewBinding!!

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
            ageCategory.text = getString(R.string.tmp_text_5)
            movieName.text = getString(R.string.tmp_text_1)
            tagline.text = getString(R.string.tmp_text_2)
            movieRating.setRating(40f)
            reviewsCounter.text = getString(R.string.tmp_text_3)
            storylineTitle.text = getString(R.string.storyline)
            storylineContent.text = getString(R.string.tmp_text_4)
            movieCast1.text = getString(R.string.tmp_name_1)
            movieCast2.text = getString(R.string.tmp_name_2)
            movieCast3.text = getString(R.string.tmp_name_3)
            movieCast4.text = getString(R.string.tmp_name_4)

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
