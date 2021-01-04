package com.example.academyfundamentalsproject

class MovieMockedData {

    companion object {

        private val AVENGERS: MovieData = MovieData(
            parentalRating = 13,
            isLiked = false,
            genre = "Action, Adventure, Drama",
            ratingPercent = 80f,
            reviewsCount = 125,
            movieName = "Avengers: End Game",
            movieLengthMinutes = 137,
            moviePicturesResource = R.drawable.card_movie_1
        )

        private val TENET: MovieData = MovieData(
            parentalRating = 16,
            isLiked = true,
            genre = "Action, Sci-Fi, Thriller",
            ratingPercent = 100f,
            reviewsCount = 98,
            movieName = "Tenet",
            movieLengthMinutes = 97,
            moviePicturesResource = R.drawable.card_movie_2
        )

        private val BLACK_WIDOW: MovieData = MovieData(
            parentalRating = 13,
            isLiked = false,
            genre = "Action, Adventure, Sci-Fi",
            ratingPercent = 80f,
            reviewsCount = 38,
            movieName = "Black Widow",
            movieLengthMinutes = 102,
            moviePicturesResource = R.drawable.card_movie_3
        )

        private val WONDER_WOMAN_1984: MovieData = MovieData(
            parentalRating = 13,
            isLiked = false,
            genre = "Action, Adventure, Fantasy",
            ratingPercent = 100f,
            reviewsCount = 74,
            movieName = "Wonder Woman 1984",
            movieLengthMinutes = 120,
            moviePicturesResource = R.drawable.card_movie_4
        )
    }

    fun getMovieData() : List<MovieData> = listOf(AVENGERS, TENET, BLACK_WIDOW, WONDER_WOMAN_1984)

}