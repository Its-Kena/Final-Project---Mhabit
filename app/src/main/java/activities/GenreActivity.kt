package activities

import adapters.GenreAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
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

        //use it within a coroutine to get the list of all movies
        GlobalScope.launch(Dispatchers.IO) {

            movieList = movieDb.movieDao().easyGetAll()

            //connect this recycler to the genre adapter and layoutmanager
            genreMovieRecycler = findViewById(R.id.mRecyclerView)
            genreMovieRecycler.layoutManager = LinearLayoutManager(this@GenreActivity)
            val adapter = GenreAdapter(this@GenreActivity, genreMoviesList)
            genreMovieRecycler.adapter = adapter
        }

        //refresh the recyclerview with this handler so that the movies can be seen
        //also update the UI on the screen according to what genre it is
        Handler(Looper.getMainLooper()).post {
            //recycler refresh
            genreMovieRecycler.adapter?.notifyDataSetChanged()

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
                genreImage.setImageResource(genreDrawable)
            }

        }
    }
}








