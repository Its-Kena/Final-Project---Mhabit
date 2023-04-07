package entities

import androidx.room.*

@Entity(tableName = "Movies")
data class Movie(
    //the following are the properties for each movie
            @ColumnInfo(name = "title")var title: String,
            @ColumnInfo(name = "description")var description: String,
            @ColumnInfo(name = "genre")var genre: Int?,
            @ColumnInfo(name = "hours")var hours: Int,
            @ColumnInfo(name = "minutes")var minutes: Int,
            @ColumnInfo(name = "rating")var rating: Float,
            @ColumnInfo(name = "review")var review: String?,
            @ColumnInfo(name = "watch again?")var WatchAgain: Boolean?,
            @PrimaryKey(autoGenerate = true) val id: Int?)

//this is where you should put the database commands *Denyka 29March23
//*need to figure out int
fun getMovie(): List<Movie> {
    val movies = mutableListOf<Movie>()
    for (i in 0..9) {
        movies.add(Movie("Title $i", "Description $i",
            0, 0, 0,
            0f, "Review $i", false, null))
    }
    return movies
}
