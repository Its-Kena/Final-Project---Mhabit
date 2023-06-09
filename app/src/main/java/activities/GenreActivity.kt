package activities

import adapters.GenreAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.GenreActivityBinding
import entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GenreActivity : AppCompatActivity() {
    private lateinit var binding: GenreActivityBinding
    private lateinit var movieDb: MovieDatabase
    private var genreChosen: String? = null
    private var movieList: MutableList<Movie>? = null
    private var genreMoviesList = mutableListOf<Movie>()
    private lateinit var genreImage : ImageView
    private lateinit var genreTitle : TextView
    private lateinit var genreDescription : TextView
    private lateinit var genreMovieRecycler : RecyclerView
    private lateinit var adapter : GenreAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GenreActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.genre_activity)

        //grab each of the views from the genre activity screen that we want to update
        genreImage = findViewById(R.id.genre_image)
        genreTitle = findViewById(R.id.genre_title)
        genreDescription = findViewById(R.id.genre_description)

        //grab the genre choice from button in main activity
        val bundle: Bundle? = intent.extras
        bundle?.apply {

            genreChosen = getString("genre") as String

        }

        //initialize the movie database
        movieDb = MovieDatabase.getDatabase(this, GlobalScope)

        //use the database within a coroutine to get the list of all movies
        GlobalScope.launch(Dispatchers.IO) {

            movieList = movieDb.movieDao().easyGetAll()

            //connect this recycler to the genre adapter and layoutmanager
            genreMovieRecycler = findViewById(R.id.mRecyclerView)
            genreMovieRecycler.layoutManager = LinearLayoutManager(this@GenreActivity)
            adapter = GenreAdapter(this@GenreActivity, genreMoviesList)
            genreMovieRecycler.adapter = adapter
        }

        //refresh the recyclerview with this handler so that the movies can be seen
        //also update the UI on the screen according to what genre it is
        Handler(Looper.getMainLooper()).post {
            //recycler refresh
            genreMovieRecycler.adapter?.notifyDataSetChanged()

            //create swipe to delete gesture and touch helper and then connect it to the recycler
            val swipegesture = object : SwipeGesture(this@GenreActivity) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    when (direction) {
                        ItemTouchHelper.LEFT -> {
                            //calls the delete function from GenreAdapter
                            adapter.deleteMovie(viewHolder.absoluteAdapterPosition)
                        }
                    }
                }
            }

            val touchHelper = ItemTouchHelper(swipegesture)
            touchHelper.attachToRecyclerView(genreMovieRecycler)

            //iterating through the list of genres [see Genre.kt] until we reach the one the user chose
            for (genre in getGenres()) {
                if (genreChosen == genre.genreName) {
                    //set the genre name and description in the activity
                    genreTitle.text = genre.genreName
                    genreDescription.text = genre.genreDescription
                    break
                }
            }

            //iterating through the all of the movies in the database
            //if the movie's genre matches the one the user chose, it gets added to a list for use by the recyclerview in the adapter
            for (movie in movieList!!) {
                if ((movie.genre).equals(genreChosen)) {
                    genreMoviesList.add(movie)
                }
            }

            //this is a when/switch case to help set the image for the genre screen
            var genreDrawable: Int? = null
            when (genreChosen) {
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
                genreImage.setImageResource(genreDrawable)
            }
        }
        //return to home
        findViewById<ImageButton>(R.id.back_to_all).setOnClickListener {
            finish()
        }
    }
}








