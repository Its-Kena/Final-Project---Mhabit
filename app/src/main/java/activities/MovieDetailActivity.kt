package activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.databinding.MovieDetailActivityBinding
import entities.Movie
import entities.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: MovieDetailActivityBinding
    private lateinit var movieDb : MovieDatabase
    private var movieItem : Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieDetailActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.movie_detail_activity)

        //return to home
        findViewById<ImageButton>(R.id.back_to_all).setOnClickListener {
            finish()
        }

        //grab the movie that was selected from the recyclerview in main activity
        val bundle: Bundle? = intent.extras
        bundle?.apply {

            //we have to account for the version and build number on user's device when using the getSerializable method
            movieItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getSerializable("movie", Movie::class.java)
            } else {
                getSerializable("movie") as Movie
            }

            //set genre image
            val genreButton = findViewById<ImageButton>(R.id.genre_button)
            var genreDrawable: Int? = null
            when (movieItem?.genre) {
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
                genreButton.setImageResource(genreDrawable)
            }

            //listener to go to the genre screen for this movie's genre
            genreButton.setOnClickListener {
                movieItem?.genre?.let { it1 -> startGenreActivity(it1) }
            }


            //set initial title
            val titleText = findViewById<EditText>(R.id.title)
            titleText.setText(movieItem?.title)

            //set initial rating
            val starBar = findViewById<RatingBar>(R.id.mhabitStarRating)

            starBar.rating = movieItem?.rating!!

            //listener to update the rating
            starBar.setOnRatingBarChangeListener { ratingBar, _, _ ->
                when(ratingBar.rating.toInt()){
                    0 -> starBar.rating = 0F
                    1 -> starBar.rating = 1F
                    2 -> starBar.rating = 2F
                    3 -> starBar.rating = 3F
                    4 -> starBar.rating = 4F
                    5 -> starBar.rating = 5F
                }
                ratingBar.rating = starBar.rating
            }

            //set initial description in movie detail screen
            val descriptionText = findViewById<EditText>(R.id.descriptionbox)
            descriptionText.setText(movieItem?.description)

            //set initial hour(s) in movie detail screen
            val hourText = findViewById<EditText>(R.id.hours)
            hourText.setText(movieItem?.hours.toString())

            //set initial minutes in movie detail screen
            val minText = findViewById<EditText>(R.id.minutes)
            minText.setText(movieItem?.minutes.toString())

            //set the initial review in movie detail screen
            val reviewText = findViewById<EditText>(R.id.reviewbox)
            if (movieItem?.review != null) {
                reviewText.setText(movieItem?.review.toString())
            }

            //set initial watchAgain value and create the dropdown box for user to choose
            var watchagain = movieItem?.WatchAgain

            val optionList = arrayOf("Yes", "No").toMutableList()

            //initialize spinner
            val spinner = findViewById<Spinner>(R.id.watchagain)
            if (spinner != null) {
                val adapter = ArrayAdapter(this@MovieDetailActivity, android.R.layout.simple_spinner_item, optionList)
                spinner.adapter = adapter
            }

            //set selected option in spinner object to whatever the initial watchAgain value is
            if (watchagain == true) {
                spinner.setSelection(0)
            }
            if (watchagain == false) {
                spinner.setSelection(1)
            }

            val a = 1
            var b = 0
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    //set watchagain according to the index of optionList since there are only two
                    if (b < a) {
                        b++
                    } else {
                        if (position == 0) {
                            watchagain = true
                        } else if (position == 1) {
                            watchagain = false
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //don't need to put anything here for our purposes right now
                }
            }

            //save edits when button is clicked
            findViewById<Button>(R.id.save).setOnClickListener {

                //update the title
                val newTitle = titleText.text.toString()
                movieItem?.title = newTitle

                //update the rating
                movieItem?.rating = starBar.rating

                //update the description
                val newDescription = descriptionText.text.toString()
                movieItem?.description = newDescription

                //update the hour(s)
                val newHours = hourText.text.toString().toInt()
                movieItem?.hours = newHours

                //update the minutes
                val newMins = minText.text.toString().toInt()
                movieItem?.minutes = newMins

                //update the review
                val review = reviewText.text.toString()
                movieItem?.review = review

                //update the watchagain value
                movieItem?.WatchAgain = watchagain

                //call the update helper method
                updateMovie()

                //exit out of this activity and return to the main activity
                finish()
            }
        }
    }

    //helper method that updates the specific movie object within the database
    private fun updateMovie() {
        movieDb = MovieDatabase.getDatabase(this, GlobalScope)
        GlobalScope.launch(Dispatchers.IO) {
            movieDb.movieDao().update(movieItem) }
    }

    //method that opens the genre view screen
    private fun startGenreActivity(genreChoice: String) {
        val genreIntent = Intent(this, GenreActivity::class.java)
        //sending the string genreChoice into the next activity so we can set attributes of screen for that specific genre
        genreIntent.putExtra("genre", movieItem?.genre)
        startActivity(genreIntent)
    }

}
