package entities

import androidx.room.ColumnInfo
import com.example.test.R

data class Genre (
    var genreName: String,
    var genreDescription: String,
)

    //create all genre objects used within the app
    var comedy =  Genre("Comedy", "Looking for a laugh? Here are the movies you listed under the comedy genre. Make sure to leave a review!")
    var thriller = Genre("Thriller", "Someone likes a thrill! These are the movies you described as \"thrillers.\"")
    var animated = Genre("Animated","Your appreciation of the hard work of artists hasn't gone unnoticed. Here are the movies you categorized in the animated genre.")
    var horror = Genre("Horror", "\"You will beware...you're in for a scare!\" You listed these movies under the horror genre.")
    var romance = Genre("Romance", "Love is a beautiful thing. Fortunately, when it comes, you'll know what to look for. Here are the movies you listed under the romance genre.")
    var action = Genre("Action","Fast-paced movies are right up your alley! You're currently looking at the action genre.")
    var other = Genre("Other", "These movies don't quite fit in the other categories. Here are the movies you listed as \"other.\"")

    //add those genre objects to an array
    val genreArray = arrayOf(comedy, thriller, animated, horror, romance, action, other)

    //conveniently return a list of all the genre objects
    fun getGenres() : List<Genre> {
        return genreArray.toMutableList()
    }

