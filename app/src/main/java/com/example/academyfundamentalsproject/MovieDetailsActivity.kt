package com.example.academyfundamentalsproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.academyfundamentalsproject.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            movieName.text = getString(R.string.tmp_text_1)
            tagline.text = getString(R.string.tmp_text_2)
            reviewsCounter.text = getString(R.string.tmp_text_3)
            storylineContent.text = getString(R.string.tmp_text_4)
            ageCategory.text = getString(R.string.tmp_text_5)
            movieCast1.text = getString(R.string.tmp_name_1)
            movieCast2.text = getString(R.string.tmp_name_2)
            movieCast3.text = getString(R.string.tmp_name_3)
            movieCast4.text = getString(R.string.tmp_name_4)
        }


    }


}