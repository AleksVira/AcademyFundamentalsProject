package com.example.academyfundamentalsproject.data

import com.example.academyfundamentalsproject.R

class MovieMockedData {

    companion object {

        var actorsStaticList: List<Actor> = listOf(
            Actor("Alicia Vikander", "https://image.ibb.co/nKNBrd/Alicia_Vikander.jpg"),
            Actor("Amanda Seyfried", "https://image.ibb.co/j142xJ/Amanda_Seyfried.jpg"),
            Actor("Anne Hathaway", "https://image.ibb.co/euy6Py/Anne_Hathaway.jpg"),
            Actor("Emma Stone", "https://image.ibb.co/dJY6Py/Emma_Stone.jpg"),
            Actor("Keira Knightley", "https://image.ibb.co/cxX0jy/Keira_Knightley.jpg"),
            Actor("George Clooney", "https://image.ibb.co/ce1t4y/George_Clooney.jpg"),
            Actor("Lucy Liu", "https://image.ibb.co/dWurrd/Lucy_Liu.jpg"),
            Actor("Matthew McConaughey", "https://image.ibb.co/e3JHWd/Matthew_Mc_Conaughey.jpg"),
            Actor("Morgan Freeman", "https://image.ibb.co/h9GhxJ/Morgan_Freeman.jpg"),
            Actor("Ryan Gosling", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
            Actor("Will Smith", "https://image.ibb.co/gxoUcJ/Will_Smith.jpg"),
            Actor("Robert de Niro", "https://image.ibb.co/e6T6Py/Robert_de_Niro.jpg"),
            Actor("Zoe Saldana", "https://image.ibb.co/i9WRPy/Zoe_Saldana.jpg")
        )


        private val AVENGERS: MovieData = MovieData(
            parentalRating = 13,
            isLiked = false,
            genre = "Action, Adventure, Drama",
            ratingPercent = 80f,
            reviewsCount = 125,
            movieName = "Avengers: End Game",
            movieLengthMinutes = 137,
            movieDescription = "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
            moviePicturesResource = R.drawable.card_movie_1,
            actorsList = getSixRandomActors()
        )

        private fun getSixRandomActors(): List<Actor> {
            return actorsStaticList.shuffled().take(6)
        }

        private val TENET: MovieData = MovieData(
            parentalRating = 16,
            isLiked = true,
            genre = "Action, Sci-Fi, Thriller",
            ratingPercent = 100f,
            reviewsCount = 98,
            movieName = "Tenet",
            movieLengthMinutes = 97,
            movieDescription = "The plot follows a secret agent (Washington) as he manipulates the flow of time to prevent World War III. Nolan took more than five years to write the screenplay after deliberating about Tenet's central ideas for over a decade.",
            moviePicturesResource = R.drawable.card_movie_2,
            actorsList = getSixRandomActors()
        )

        private val BLACK_WIDOW: MovieData = MovieData(
            parentalRating = 13,
            isLiked = false,
            genre = "Action, Adventure, Sci-Fi",
            ratingPercent = 80f,
            reviewsCount = 38,
            movieName = "Black Widow",
            movieLengthMinutes = 102,
            movieDescription = "In Marvel Studios' action-packed spy thriller “Black Widow,” Natasha Romanoff aka Black Widow confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises.",
            moviePicturesResource = R.drawable.card_movie_3,
            actorsList = getSixRandomActors()
        )

        private val WONDER_WOMAN_1984: MovieData = MovieData(
            parentalRating = 13,
            isLiked = false,
            genre = "Action, Adventure, Fantasy",
            ratingPercent = 100f,
            reviewsCount = 74,
            movieName = "Wonder Woman 1984",
            movieLengthMinutes = 120,
            movieDescription = "Wonder Woman 1984 (stylized as WW84) is a 2020 American superhero film based on the DC Comics character Wonder Woman. ... Set in 1984 during the Cold War, the film follows Diana and her past love Steve Trevor as they face off against Max Lord and Cheetah.",
            moviePicturesResource = R.drawable.card_movie_4,
            actorsList = getSixRandomActors()
        )
    }

    fun getMovieData(): List<MovieData> = listOf(AVENGERS, TENET, BLACK_WIDOW, WONDER_WOMAN_1984)

}