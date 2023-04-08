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

        holder.movieTitle.text = currentMovie.title
        holder.movieDescription.text = currentMovie.description
        holder.movieRating.rating = currentMovie.rating

        //switch case for genreImage; commented out until drawables are uploaded
        var genreDrawable : Int? = null
        when (currentMovie.genre) {
            "Comedy" -> genreDrawable = R.drawable.mhabit_comedy
            "Thriller" -> genreDrawable = 0
            "Animated" -> genreDrawable = 0
            "Horror" -> genreDrawable = 0
            "Romance" -> genreDrawable = 0
            "Action" -> genreDrawable = 0
            "Other" -> genreDrawable = 0
            else -> {
                print("ERROR: genre is none of the above")
            }
        }

        if (genreDrawable != null) {
            holder.movieImage.setImageResource(genreDrawable)
        }


        //starts MovieDetailActivity when a movie is clicked
        holder.itemView.setOnClickListener {
            startMovieDetails(holder.itemView, currentMovie)
        }
    }

    fun deleteMovie(i : Int) {
        // initialize the database
        movieDb = MovieDatabase.getDatabase(mContext as GenreActivity, GlobalScope)
        //use coroutine to carry out action
        GlobalScope.launch(Dispatchers.IO) {
            movieDb.movieDao().delete(dataSet[i])
        }
    }

    private fun startMovieDetails(view: View, movie: Movie) {
        val intent = Intent(mContext as GenreActivity, MovieDetailActivity::class.java)
        intent.putExtra("movie", movie as Serializable)

        ContextCompat.startActivity(mContext as GenreActivity, intent, null)
    }


    //need to change this to only be the amount of movies in that genre-->so a genreMovieListSet
    override fun getItemCount() = dataSet.size

}