package entities

import androidx.room.*

@Entity
class Movie(val title: String,
    //the following are the properties for each movie
            var rating: Float,
            var description: String,
            val genrePic: Int,
            val minutes: Int,
            val seconds: Int,
            val hours: Int,
            val review: String,
            val WatchAgain: Boolean,
            @PrimaryKey val id: Long? = null)

//this is where you should put the database commands *Denyka 29March23
//*need to figure out int
fun getMovie(): List<Movie> {
    val movies = mutableListOf<Movie>()
    for (i in 0..9) {
        movies.add(Movie("Title $i", 0f,
            "Description $i",0, 0, 0,
            0, "Rewatch $i", false))
    }
    return movies
}
@Dao
interface MovieDao{
    @Query("select * from movie")
    suspend fun getAll(): List<Movie>

    @Insert
    suspend fun insert(movie: entities.Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

data class Movie(//the following are the properties for each movie
    val genrePic: Int,
    val title: String,
    val rating: Float,
    val description: String,
    val minutes: Int,
    val seconds: Int,
    val hours: Int,
    val review: String,
    val WatchAgain: Boolean)


}