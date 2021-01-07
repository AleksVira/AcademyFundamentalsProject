package com.example.academyfundamentalsproject.data

import com.example.academyfundamentalsproject.R

class MovieMockedData {

    companion object {

        var actorsStaticList: List<ActorData> = listOf(
            ActorData(1, "Alicia Vikander", "https://image.ibb.co/nKNBrd/Alicia_Vikander.jpg"),
            ActorData(2,"Amanda Seyfried", "https://image.ibb.co/j142xJ/Amanda_Seyfried.jpg"),
            ActorData(3, "Anne Hathaway", "https://image.ibb.co/euy6Py/Anne_Hathaway.jpg"),
            ActorData(4,"Emma Stone", "https://image.ibb.co/dJY6Py/Emma_Stone.jpg"),
            ActorData(5,"Keira Knightley", "https://image.ibb.co/cxX0jy/Keira_Knightley.jpg"),
            ActorData(6,"George Clooney", "https://image.ibb.co/ce1t4y/George_Clooney.jpg"),
            ActorData(7,"Lucy Liu", "https://image.ibb.co/dWurrd/Lucy_Liu.jpg"),
            ActorData(8,"Matthew McConaughey", "https://image.ibb.co/e3JHWd/Matthew_Mc_Conaughey.jpg"),
            ActorData(9,"Morgan Freeman", "https://image.ibb.co/h9GhxJ/Morgan_Freeman.jpg"),
            ActorData(10,"Ryan Gosling", "https://image.ibb.co/buLLjy/Ryan_Gosling.jpg"),
            ActorData(11,"Will Smith", "https://image.ibb.co/gxoUcJ/Will_Smith.jpg"),
            ActorData(12,"Robert de Niro", "https://image.ibb.co/e6T6Py/Robert_de_Niro.jpg"),
            ActorData(13,"Zoe Saldana", "https://image.ibb.co/i9WRPy/Zoe_Saldana.jpg")
        )

        private fun getSixRandomActors(): List<ActorData> {
            return actorsStaticList.shuffled().take(6)
        }

        private val AVENGERS: MovieData = MovieData(
            pgAge = 13,
            isLiked = false,
//            genre = "Action, Adventure, Drama",
            ratingPercent = 80f,
            reviewsCount = 125,
            movieName = "Avengers: End Game",
            movieLengthMinutes = 137,
            storyLine = "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
//            moviePicturesResource = R.drawable.card_movie_1,
            actorsList = getSixRandomActors()
        )

        private val TENET: MovieData = MovieData(
            pgAge = 16,
            isLiked = true,
//            genre = "Action, Sci-Fi, Thriller",
            ratingPercent = 100f,
            reviewsCount = 98,
            movieName = "Tenet",
            movieLengthMinutes = 97,
            storyLine = "The plot follows a secret agent (Washington) as he manipulates the flow of time to prevent World War III. Nolan took more than five years to write the screenplay after deliberating about Tenet's central ideas for over a decade.",
//            moviePicturesResource = R.drawable.card_movie_2,
            actorsList = getSixRandomActors()
        )

        private val BLACK_WIDOW: MovieData = MovieData(
            pgAge = 13,
            isLiked = false,
//            genre = "Action, Adventure, Sci-Fi",
            ratingPercent = 80f,
            reviewsCount = 38,
            movieName = "Black Widow",
            movieLengthMinutes = 102,
            storyLine = "In Marvel Studios' action-packed spy thriller “Black Widow,” Natasha Romanoff aka Black Widow confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises.",
//            moviePicturesResource = R.drawable.card_movie_3,
            actorsList = getSixRandomActors()
        )

        private val WONDER_WOMAN_1984: MovieData = MovieData(
            pgAge = 13,
            isLiked = false,
//            genre = "Action, Adventure, Fantasy",
            ratingPercent = 100f,
            reviewsCount = 74,
            movieName = "Wonder Woman 1984",
            movieLengthMinutes = 120,
            storyLine = "Wonder Woman 1984 (stylized as WW84) is a 2020 American superhero film based on the DC Comics character Wonder Woman. ... Set in 1984 during the Cold War, the film follows Diana and her past love Steve Trevor as they face off against Max Lord and Cheetah.",
//            moviePicturesResource = R.drawable.card_movie_4,
            actorsList = getSixRandomActors()
        )

        private val LORD_OF_WAR: MovieData = MovieData(
            pgAge = 16,
            isLiked = true,
//            genre = "Action, Crime, Drama",
            ratingPercent = 63f,
            reviewsCount = 152,
            movieName = "Lord of War",
            movieLengthMinutes = 121,
            storyLine = "Lord of War is a 2005 American crime drama film written, produced, and directed by Andrew Niccol, and co-produced by and starring Nicolas Cage. ... Cage plays an illegal arms dealer, inspired by the stories of several real-life arms dealers and smugglers.",
//            moviePicturesResource = R.drawable.card_movie_5,
            actorsList = getSixRandomActors()
        )

        private val LEGION: MovieData = MovieData(
            pgAge = 16,
            isLiked = false,
//            genre = "Action, Fantasy, Horror",
            ratingPercent = 53f,
            reviewsCount = 98,
            movieName = "Legion",
            movieLengthMinutes = 100,
            storyLine = "Legion is a 2010 American action horror film directed by Scott Stewart and co-written by Stewart and Peter Schink. The cast includes Paul Bettany, Lucas Black, Tyrese Gibson, Adrianne Palicki, Kate Walsh, and Dennis Quaid. Sony Pictures Worldwide Acquisitions Group acquired most of this film's worldwide distribution rights, and the group opened this film in North America theatrically on January 22, 2010, through Screen Gems.",
//            moviePicturesResource = R.drawable.card_movie_6,
            actorsList = getSixRandomActors()
        )
    }

    fun getMovieData(): List<MovieData> = listOf(AVENGERS, TENET, BLACK_WIDOW, WONDER_WOMAN_1984, LORD_OF_WAR, LEGION)

}