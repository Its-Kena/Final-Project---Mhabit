package activities

import adapters.MovieAdapter
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.MhabitHomeBinding
import entities.*
import java.io.Serializable


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: MhabitHomeBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var movieList: List<Movie>
    private lateinit var extraMovieList : List<Movie>


    //viewmodel used to work with the database on main thread
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.MovieViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MhabitHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.mhabit_home) //this will be changed to the layout name of our main screen once the app is complete. this defines where the user will start upon opening the app.

        val comedyButton = findViewById<Button>(R.id.comedy)
        val thrillerButton = findViewById<Button>(R.id.thriller)
        val animatedButton = findViewById<Button>(R.id.animated)
        val horrorButton = findViewById<Button>(R.id.horror)
        val romanceButton = findViewById<Button>(R.id.romance)
        val actionButton = findViewById<Button>(R.id.action)
        val otherButton = findViewById<Button>(R.id.other)

        comedyButton.setOnClickListener(this)
        thrillerButton.setOnClickListener(this)
        animatedButton.setOnClickListener(this)
        horrorButton.setOnClickListener(this)
        romanceButton.setOnClickListener(this)
        actionButton.setOnClickListener(this)
        otherButton.setOnClickListener(this)

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
                        when (direction) {
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

        addButton.setOnClickListener {
            //see ViewDialog file
            val alert = ViewDialog(this@MainActivity)
            alert.showAddMovieDialog(this)
            binding.allMovies.smoothScrollToPosition(MovieAdapter(this, movieList).itemCount - 1)


        }

        /*
        //Bryce's work for Friday's demo
        val b1 = findViewById<Button>(R.id.comedy)
        b1.setOnClickListener {
            val Intent = Intent(this, GenreActivity::class.java)
            startActivity(Intent)
        }

         */
    }




    private fun startGenreActivity(genreToChoose: String) {
        val genreIntent = Intent(this, GenreActivity::class.java)
        genreIntent.putExtra("genre", genreToChoose)
        startActivity(genreIntent)
    }



override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.comedy -> startGenreActivity("Comedy")

            R.id.thriller -> startGenreActivity("Thriller")

            R.id.animated -> startGenreActivity("Animated")

            R.id.horror -> startGenreActivity("Horror")

            R.id.romance -> startGenreActivity("Romance")

            R.id.action -> startGenreActivity("Action")

            R.id.other -> startGenreActivity("Other")

            else -> {
                print("ERROR: id is none of the above")
            }
        }
    }
}


