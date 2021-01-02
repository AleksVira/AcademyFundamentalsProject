package com.example.academyfundamentalsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), MovieCardClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentHolder, FragmentMoviesList())
                .commit()
        }
    }

    override fun onMovieCardClicked() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, FragmentMoviesDetails())
            .addToBackStack(null)
            .commit()
    }
}