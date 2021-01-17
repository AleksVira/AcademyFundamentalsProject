package com.example.academyfundamentalsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.academyfundamentalsproject.main_list.FragmentMoviesList
import com.example.academyfundamentalsproject.movie_detail.FragmentMoviesDetails

class MainActivity : AppCompatActivity(), FragmentMoviesList.MovieCardClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentHolder, FragmentMoviesList())
                .commit()
        }
    }

    override fun onMovieCardSelected() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, FragmentMoviesDetails())
            .addToBackStack(FragmentMoviesDetails::class.java.name)
            .commit()
    }
}