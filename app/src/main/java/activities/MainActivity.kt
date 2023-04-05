package activities

import adapters.MovieAdapter
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.*
import androidx.room.Room
import com.example.test.R
import com.example.test.databinding.MhabitHomeBinding
import entities.Movie
import entities.MovieDao
import entities.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity() {
    //added private variables for DAO *Denyka 29MArch23
    private lateinit var binding: MhabitHomeBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var movieDao: MovieDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MhabitHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.mhabit_home) //this will be changed to the layout name of our main screen once the app is complete. this defines where the user will start upon opening the app.

        val db = Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java, DATABASE_NAME
        ).build()




        var recycler = findViewById<RecyclerView>(R.id.all_movies)


    }
    // addMovie *Denyka 29March23 *need to create recycler
    fun addMovie(view: View) {

        val adapter = binding.allMovies.adapter as MovieAdapter
        adapter.add(Movie("New Movie", 0f, "New Description",
            0,0,0,0,"New Review", false))
        binding.allMovies.smoothScrollToPosition(adapter.itemCount)
        runOnIO{movieDao.insert(Movie("New Movie",0f, "New Description",
        0,0,0,0, "New Review",false))}//takes pff main thread puts on IO thread
    }
    //companion object *Denyka 29March23
    companion object {
        const val DATABASE_NAME = "app_database"
        const val PREFS_NAME = "app_prefs"
        const val IS_DOWNLOADED_KEY = "isDownloaded"
    }
    fun runOnIO(lambda: suspend () -> Unit) {
        runBlocking {
            launch(Dispatchers.IO) { lambda() }
        }
    }
}



