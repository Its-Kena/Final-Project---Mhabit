package entities

import androidx.room.ColumnInfo
import com.example.test.R

data class Genre (

    var genreName: String,
    var genreDescription: String,
        ) : java.io.Serializable

    var comedy =  Genre("Comedy", "Looking for a laugh? Here are the movies you listed under the comedy genre. Make sure to leave a review!")
    var thriller = Genre("Thriller", "")
    var animated = Genre("Animated","")
    var horror = Genre("Horror", "")
    var romance = Genre("Romance", "")
    var action = Genre("Action","")
    var other = Genre("Other", "")

val genreArray = arrayOf(comedy, thriller, animated, horror, romance, action, other)

fun getGenres() : List<Genre> {
    return genreArray.toMutableList()
}

