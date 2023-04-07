package activities

import adapters.MovieAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.*
import androidx.room.Room
import com.example.test.R
import com.example.test.databinding.MhabitHomeBinding
import entities.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    //added private variables for DAO *Denyka 29MArch23
    private lateinit var binding: MhabitHomeBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var adapter: MovieAdapter

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.MovieViewModelFactory((application as MyApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MhabitHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.mhabit_home) //this will be changed to the layout name of our main screen once the app is complete. this defines where the user will start upon opening the app.



        movieViewModel.allMovies.observe(this, Observer { movies ->
            movies?.let {

                val allMoviesRecycler = findViewById<RecyclerView>(R.id.all_movies)
                val adapter = MovieAdapter(movies)
                allMoviesRecycler.adapter = adapter
                allMoviesRecycler.layoutManager = LinearLayoutManager(this)

            }

        })



        var allGenresRecycler = findViewById<RecyclerView>(R.id.genre_recycler)

        var addButton = findViewById<Button>(R.id.add_movie)

        addButton.setOnClickListener{
            val alert = ViewDialog(this@MainActivity)
            alert.showAddMovieDialog(this)

        }
    }


}
    // addMovie *Denyka 29March23 *need to create recycler
    /*
    fun addMovie(view: View) {

        val adapter = binding.allMovies.adapter as MovieAdapter
        adapter.add(Movie("New Movie", 0f, "New Description",
            0,0,0,0,"New Review", false, null))
        binding.allMovies.smoothScrollToPosition(adapter.itemCount)
        runOnIO{movieDao.insert(Movie("New Movie",0f, "New Description",
        0,0,0,0, "New Review",false, null))}//takes pff main thread puts on IO thread
    }


     */
    //companion object *Denyka 29March23

    fun runOnIO(lambda: suspend () -> Unit) {
        runBlocking {
            launch(Dispatchers.IO) { lambda() }
        }
    }




