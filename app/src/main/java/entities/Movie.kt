package entities

import activities.MainActivity
import android.content.Context
import android.os.Parcelable
import androidx.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.Serializable
import kotlinx.parcelize.Parcelize


@Entity(tableName = "Movies")
data class Movie  (
    //the following are the properties for each movie
            @ColumnInfo(name = "title")var title: String,
            @ColumnInfo(name = "description")var description: String,
            @ColumnInfo(name = "genre")var genre: String?,
            @ColumnInfo(name = "hours")var hours: Int,
            @ColumnInfo(name = "minutes")var minutes: Int,
            @ColumnInfo(name = "rating")var rating: Float,
            @ColumnInfo(name = "review")var review: String?,
            @ColumnInfo(name = "watch again?")var WatchAgain: Boolean?,
            @PrimaryKey(autoGenerate = true) val id: Int?) : Serializable

/*

fun getMovie(context: Context): List<Movie> {
    val movies = mutableListOf<Movie>()
    var mContext = context
    var movieDb = MovieDatabase.getDatabase(mContext, GlobalScope)
    //use coroutine to carry out action

    for (i in iterator<Flow<List<Movie>>> { movieDb.movieDao().getAll( )} ) {
        movies.add(i)
    }
    return movies
}

 */
