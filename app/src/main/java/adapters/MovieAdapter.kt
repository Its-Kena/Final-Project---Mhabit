package adapters


import activities.MainActivity
import activities.MovieDetailActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import entities.Movie
import entities.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable


class MovieAdapter(context: Context, private val dataSet: List<Movie>): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private lateinit var movieDb : MovieDatabase
    private var mContext = context
    //the dataSet is the movieList from MainActivity.kt


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //connects to the different views in the movie_card.xml

        val image: ImageView = view.findViewById(R.id.feat_genre)
        val title: TextView = view.findViewById(R.id.feat_title)
        val description: TextView = view.findViewById(R.id.feat_description)
        val rating: RatingBar = view.findViewById(R.id.feat_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.movie_card, parent, false)

    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataSet[position]

        //set the movie's attributes for its card that will appear in the recyclerview
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        holder.rating.rating = currentItem.rating

        var genreDrawable : Int? = null
        when (currentItem.genre) {
            "Comedy" -> genreDrawable = R.drawable.mhabit_comedy
            "Thriller" -> genreDrawable = 0
            "Animated" -> genreDrawable = 0
            "Horror" -> genreDrawable = 0
            "Romance" -> genreDrawable = 0
            "Action" -> genreDrawable = R.drawable.mhabit_action
            "Other" -> genreDrawable = 0
            else -> {
                print("ERROR: genre is none of the above")
            }
        }

        if (genreDrawable != null) {
            holder.image.setImageResource(genreDrawable)
        }

        //starts the movie detail screen for whichever movie the user clicks
        //sending the movie they clicked into the next activity
        holder.itemView.setOnClickListener {
            startMovieDetails(holder.itemView, currentItem)
        }
    }

    //called in MainActivity.kt when the user swipes a movie to the left in the recyclerview
    //deletes the specific movie object from the database
    fun deleteMovie(i : Int) {
        movieDb = MovieDatabase.getDatabase(mContext as MainActivity, GlobalScope)
        GlobalScope.launch(Dispatchers.IO) {
            movieDb.movieDao().delete(dataSet[i])
        }
    }

    //method that opens the movie details screen
    private fun startMovieDetails(view: View, movie: Movie) {
        val intent = Intent(mContext as MainActivity, MovieDetailActivity::class.java)
        intent.putExtra("movie", movie as Serializable)
        startActivity(mContext as MainActivity,intent, null)
    }

    override fun getItemCount() = dataSet.size

}