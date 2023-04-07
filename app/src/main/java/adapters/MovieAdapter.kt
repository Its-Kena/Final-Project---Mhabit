package adapters


import activities.MainActivity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import entities.Movie
import com.example.test.R
import entities.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieAdapter(context: Context, private val dataSet: List<Movie>): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val data = dataSet.toMutableList()
    private lateinit var movieDb : MovieDatabase
    var mContext = context

    //= dataSet.toMutableList()
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //connects to the 4 different views in the movie_card.xml
        val image: ImageView = view.findViewById(R.id.feat_genre)
        val title: TextView = view.findViewById<TextView>(R.id.feat_title)
        val description: TextView = view.findViewById<TextView>(R.id.feat_description)
        val rating: RatingBar = view.findViewById(R.id.feat_rating)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_card, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //dataSet is what holds all the movies
        val currentItem = data[position]

        //holder.image.setImageResource(currentItem.genre!!)//genrePic resource is defined in movie.kt class for movie image
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        holder.rating.rating = currentItem.rating
        //the first rating is defined in ViewHolder above and  the second is defined in movie.kt
    }

    // created add function 30March23 *Denyka
    fun add(movie: Movie) {
        data.add(movie)
        notifyItemInserted(data.size - 1)
    }

    fun deleteMovie(i : Int) {
        movieDb = MovieDatabase.getDatabase(mContext as MainActivity, GlobalScope)
        GlobalScope.launch(Dispatchers.IO) {

            movieDb.movieDao().delete(data[i])
        }
    }

    override fun getItemCount() = dataSet.size

}