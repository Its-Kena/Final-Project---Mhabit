package entities

class Movie(val title: String,
    //the following are the properties for each movie
            val rating: Int,
            val description: String,
            val minutes: Int,
            val seconds: Int,
            val hours: Int,
            val review: String,
            val WatchAgain: Boolean)

//this is where you should put the database commands