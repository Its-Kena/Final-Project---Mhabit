package activities

import adapters.MovieAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.*
import androidx.room.Room
import com.example.test.R
import com.example.test.databinding.MhabitHomeBinding
import entities.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: MhabitHomeBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var movieList : List<Movie>

    //viewmodel used to work with the database on main thread
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.MovieViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MhabitHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.mhabit_home) //this will be changed to the layout name of our main screen once the app is complete. this defines where the user will start upon opening the app.


    //observer that automatically updates database as needed
        movieViewModel.allMovies.observe(this, Observer { movies ->
            movies?.let {
                //this has already grabbed the list of movies for us so we can just use it
                //connect allMoviesRecycler to appropriate adapter
                movieList = movies
                val allMoviesRecycler = findViewById<RecyclerView>(R.id.all_movies)
                val adapter = MovieAdapter(this, movies)
                allMoviesRecycler.adapter = adapter
                allMoviesRecycler.layoutManager = LinearLayoutManager(this)

                //create swipe gesture and touch helper and then connect it to the recycler we just made
                val swipegesture = object : SwipeGesture(this) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        when(direction) {
                            ItemTouchHelper.LEFT -> {
                                //calls the delete function from MovieAdapter
                                adapter.deleteMovie(viewHolder.absoluteAdapterPosition)
                            }
                        }
                    }
                }

                val touchHelper = ItemTouchHelper(swipegesture)
                touchHelper.attachToRecyclerView(allMoviesRecycler)
            }

        })


        //var allGenresRecycler = findViewById<RecyclerView>(R.id.genre_recycler)

        //set up pop up for adding movie
        var addButton = findViewById<Button>(R.id.add_movie)

        addButton.setOnClickListener{
            //see ViewDialog file
            val alert = ViewDialog(this@MainActivity)
            alert.showAddMovieDialog(this)
            binding.allMovies.smoothScrollToPosition(MovieAdapter(this, movieList).itemCount-1)


        }

        // makes the comedy genre button work - Bryce
        val b1 = findViewById<Button>(R.id.genre1)
        b1.setOnClickListener {
            val Intent = Intent(this, ComedyActivity::class.java)
            startActivity(Intent)
        }





    }


}

//not sure if we need the code below at the moment but keeping it just in case

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



    //companion object *Denyka 29March23

    fun runOnIO(lambda: suspend () -> Unit) {
        runBlocking {
            launch(Dispatchers.IO) { lambda() }
        }
    }

     */



