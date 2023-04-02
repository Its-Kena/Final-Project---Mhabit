package entities

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

//this is where you should put the database commands