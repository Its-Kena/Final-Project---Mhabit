package entities

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "Movies")
data class Movie  (
    @ColumnInfo(name = "Title")         var title: String,
    @ColumnInfo(name = "Description")   var description: String,
    @ColumnInfo(name = "Genre")         var genre: String?,
    @ColumnInfo(name = "Hours")         var hours: Int,
    @ColumnInfo(name = "Minutes")       var minutes: Int,
    @ColumnInfo(name = "Rating")        var rating: Float,
    @ColumnInfo(name = "Review")        var review: String?,
    @ColumnInfo(name = "Watch again?")  var WatchAgain: Boolean?,
    @PrimaryKey(autoGenerate = true)    val id: Int?
    ) : Serializable

//made serializable so that movie objects can be passed between activities with an intent


