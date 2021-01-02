package com.example.academyfundamentalsproject

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.academyfundamentalsproject.databinding.FragmentMoviesListBinding

class FragmentMoviesList : Fragment() {

    private var movieCardClickListener: MovieCardClickListener? = null

    private var _viewBinding: FragmentMoviesListBinding? = null
    private val viewBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewBinding = FragmentMoviesListBinding.inflate(inflater, container, false)

        viewBinding.root.setOnClickListener{
            movieCardClickListener?.onMovieCardClicked()
        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.moviesListHeader.text = "Movies list"
        with(viewBinding.movieCard) {
            parentalGuidanceMark.text = getString(R.string.tmp_text_5)
            movieName.text = getString(R.string.tmp_text_6)
            lengthMin.text = getString(R.string.tmp_text_7)
            itemMovieRating.setRating(40f)
            reviewsCounter.text = getString(R.string.tmp_text_3)
            itemTagline.text = getString(R.string.tmp_text_8)
        }
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
        _viewBinding = null
    }



}