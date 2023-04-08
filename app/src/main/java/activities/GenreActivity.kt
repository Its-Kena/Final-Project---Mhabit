package activities

import adapters.GenreAdapter
import adapters.MovieAdapter
import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.NavType
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.GenreActivityBinding
import entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GenreActivity : AppCompatActivity() {
    private lateinit var binding: GenreActivityBinding
    private lateinit var genreList: List<Genre>
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

        genreImage = findViewById(R.id.genre_image)
        genreTitle = findViewById(R.id.genre_title)
        genreDescription = findViewById(R.id.genre_description)

        val bundle: Bundle? = intent.extras
        bundle?.apply {

            genreChosen = getString("genre") as String
            //println(genreChosen)


        }
        movieDb = MovieDatabase.getDatabase(this, GlobalScope)

        GlobalScope.launch(Dispatchers.IO) {

            movieList = movieDb.movieDao().easyGetAll()

            genreMovieRecycler = findViewById(R.id.mRecyclerView)
            genreMovieRecycler.layoutManager = LinearLayoutManager(this@GenreActivity)
            var adapter = GenreAdapter(this@GenreActivity, genreMoviesList, genreChosen)
            genreMovieRecycler.adapter = adapter
        }

        Handler(Looper.getMainLooper()).post {
            genreMovieRecycler.adapter?.notifyDataSetChanged()
            for (movie in movieList!!) {
                if ((movie.genre).equals(genreChosen)) {
                    genreMoviesList.add(movie)
                }

                for (genre in getGenres()) {
                    if (genreChosen == genre.genreName) {
                        //set the genre name in the activity
                        genreTitle.text = genre.genreName
                        //set the genre description in the activity
                        genreDescription.text = genre.genreDescription
                    }
                }

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
                    genreImage.setImageResource(genreDrawable!!)
                }

            }
        }
    }

}








