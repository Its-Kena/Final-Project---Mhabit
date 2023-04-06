package entities

import androidx.room.*

@Entity(tableName = "Movies")
data class Movie(
    @ColumnInfo(name = "title")val title: String,
    //the following are the properties for each movie
            @ColumnInfo(name = "rating")var rating: Float,
                 @ColumnInfo(name = "description")var description: String,
                 @ColumnInfo(name = "genre")val genrePic: Int,
                 @ColumnInfo(name = "minutes")val minutes: Int,
                 @ColumnInfo(name = "seconds")val seconds: Int,
                 @ColumnInfo(name = "hours")val hours: Int,
                 @ColumnInfo(name = "review")val review: String,
                 @ColumnInfo(name = "watch again?")val WatchAgain: Boolean,
            @PrimaryKey(autoGenerate = true) val id: Int?)

//this is where you should put the database commands *Denyka 29March23
//*need to figure out int
fun getMovie(): List<Movie> {
    val movies = mutableListOf<Movie>()
    for (i in 0..9) {
        movies.add(Movie("Title $i", 0f,
            "Description $i",0, 0, 0,
            0, "Review $i", false, 0))
    }
    return movies
}



    /*data class Movie(//the following are the properties for each movie
    val genrePic: Int,
    val title: String,
    val rating: Float,
    val description: String,
    val minutes: Int,
    val seconds: Int,
    val hours: Int,
    val review: String,
    val WatchAgain: Boolean,
    val id: Int?)




}

     */