package adapters

import activities.GenreActivity
import activities.MovieDetailActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import entities.Movie
import entities.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable

class GenreAdapter(context: Context, private val dataSet: List<Movie>) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    private lateinit var movieDb : MovieDatabase
    private var mContext = context
    //the dataSet is the genreMoviesList from GenreActivity.kt


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //connects to the different views in the movie_card.xml

        val movieImage: ImageView = view.findViewById(R.id.feat_genre)
        val movieTitle: TextView = view.findViewById(R.id.feat_title)
        val movieDescription: TextView = view.findViewById(R.id.feat_description)
        val movieRating: RatingBar = view.findViewById(R.id.feat_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.movie_card, parent, false)

    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMovie = dataSet[position]

        //set the movie's attributes for its card that will appear in the recyclerview
        holder.movieTitle.text = currentMovie.title
        holder.movieDescription.text = currentMovie.description
        holder.movieRating.rating = currentMovie.rating


        var genreDrawable : Int? = null
        when (currentMovie.genre) {
            "Comedy" -> genreDrawable = R.drawable.mhabit_comedy
            "Thriller" -> genreDrawable = R.drawable.mhabit_thriller
            "Animated" -> genreDrawable = R.drawable.mhabit_anime
            "Horror" -> genreDrawable = R.drawable.mhabit_horror
            "Romance" -> genreDrawable = R.drawable.mhabit_romance
            "Action" -> genreDrawable = R.drawable.mhabit_action
            "Other" -> genreDrawable = R.drawable.mhabit_other
            else -> {
                print("ERROR: genre is none of the above")
            }
        }

        if (genreDrawable != null) {
            holder.movieImage.setImageResource(genreDrawable)
        }

        //starts the movie detail screen for whichever movie the user clicks
        //sending the movie they clicked into the next activity
        holder.itemView.setOnClickListener {
            startMovieDetails(holder.itemView, currentMovie)
        }
    }

    //called in GenreActivity.kt when the user swipes a movie to the left in the recyclerview
    //deletes the specific movie object from the database
    fun deleteMovie(i : Int) {
        movieDb = MovieDatabase.getDatabase(mContext as GenreActivity, GlobalScope)
        GlobalScope.launch(Dispatchers.IO) {
            movieDb.movieDao().delete(dataSet[i])
        }
    }

    //method that opens the movie details screen
    private fun startMovieDetails(view: View, movie: Movie) {
        val intent = Intent(mContext as GenreActivity, MovieDetailActivity::class.java)
        intent.putExtra("movie", movie as Serializable)
        ContextCompat.startActivity(mContext as GenreActivity, intent, null)
    }

    override fun getItemCount() = dataSet.size

}