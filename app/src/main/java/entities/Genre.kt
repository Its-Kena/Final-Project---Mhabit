package entities

import androidx.room.ColumnInfo
import com.example.test.R

data class Genre (
    var genreName: String,
    var genreDescription: String,
)

    //create all genre objects used within the app
    var comedy =  Genre("Comedy", "Looking for a laugh? Here are the movies you listed under the comedy genre. Make sure to leave a review!")
    var thriller = Genre("Thriller", "")
    var animated = Genre("Animated","")
    var horror = Genre("Horror", "")
    var romance = Genre("Romance", "")
    var action = Genre("Action","")
    var other = Genre("Other", "")

    //add those genre objects to an array
    val genreArray = arrayOf(comedy, thriller, animated, horror, romance, action, other)

    //conveniently return a list of all the genre objects
    fun getGenres() : List<Genre> {
        return genreArray.toMutableList()
    }

