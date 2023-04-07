package activities

import adapters.MovieAdapter
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
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
        setContentView(R.layout.mhabit_home)

//        val db = Room.databaseBuilder(
//            applicationContext,
//            MovieDatabase::class.java, DATABASE_NAME
//        ).build()

        // sets up working genre buttons to be clicked on to go to second activity
//        val b1 = findViewById<Button>(R.id.genre1)
//        b1.setOnClickListener {
//            val Intent = Intent(this, GenreActivity::class.java)
//            startActivity(Intent)
//        }
//        val b2 = findViewById<Button>(R.id.genre2)
//        b2.setOnClickListener {
//            val Intent = Intent(this, GenreActivity::class.java)
//            startActivity(Intent)
//        }
//        val b3 = findViewById<Button>(R.id.genre3)
//        b3.setOnClickListener {
//            val Intent = Intent(this, GenreActivity::class.java)
//            startActivity(Intent)
//        }
//        val b4 = findViewById<Button>(R.id.genre4)
//        b4.setOnClickListener {
//            val Intent = Intent(this, GenreActivity::class.java)
//            startActivity(Intent)
//        }
//        val b5 = findViewById<Button>(R.id.genre5)
//        b5.setOnClickListener {
//            val Intent = Intent(this, GenreActivity::class.java)
//            startActivity(Intent)
//        }
//        val b6 = findViewById<Button>(R.id.genre6)
//        b6.setOnClickListener {
//            val Intent = Intent(this, GenreActivity::class.java)
//            startActivity(Intent)
//        }
//        val b7 = findViewById<Button>(R.id.genre7)
//        b7.setOnClickListener {
//            val Intent = Intent(this, GenreActivity::class.java)
//            startActivity(Intent)
//        }



        // var recycler = findViewById<RecyclerView>(R.id.all_movies)


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



