package activities

import adapters.MovieAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.MhabitHomeBinding
import entities.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: MhabitHomeBinding
    private lateinit var movieList: List<Movie>


    //viewmodel used to work with the database on main thread
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.MovieViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MhabitHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.mhabit_home) //this will be changed to the layout name of our main screen once the app is complete. this defines where the user will start upon opening the app.




        //grab buttons and attach onClickListeners for future use with a convenient when/switch case
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
                //connect allMoviesRecycler to appropriate adapter and set its layout manager
                movieList = movies
                val allMoviesRecycler = findViewById<RecyclerView>(R.id.all_movies)
                val adapter = MovieAdapter(this, movieList)
                allMoviesRecycler.adapter = adapter
                allMoviesRecycler.layoutManager = LinearLayoutManager(this)





                //create the swipe to delete gesture and touch helper and then connect it to the recycler
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

                //scroll to position of newly added item
                allMoviesRecycler.scrollToPosition(adapter.itemCount - 1)

            }
        })

        //set up pop up for adding movie
        var addButton = findViewById<Button>(R.id.add_movie)

        addButton.setOnClickListener {

            // See ViewDialog file
            val alert = ViewDialog(this@MainActivity)
            alert.showAddMovieDialog(this)

            //this ScrollToPosition doesn't work --> needs to be fixed
            //binding.allMovies.smoothScrollToPosition(MovieAdapter(this, movieList).itemCount - 1)


        }

    }

    //method that opens the genre view screen
    private fun startGenreActivity(genreChoice: String) {
        val genreIntent = Intent(this, GenreActivity::class.java)
        //sending the string genreChoice into the next activity so we can set attributes of screen for that specific genre
        genreIntent.putExtra("genre", genreChoice)
        startActivity(genreIntent)
    }

    //calls the method to start the genre activity when any of the genre buttons on the main screen are clicked
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


