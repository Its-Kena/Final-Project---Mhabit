package activities

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import android.content.Intent
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

        //grab the movie that was selected from the recyclerview in main activity
        val bundle: Bundle? = intent.extras
        bundle?.apply {

            //we have to account for the version and build number on user's device when using the getSerializable method
            movieItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getSerializable("movie", Movie::class.java)
            } else {
                getSerializable("movie") as Movie
            }


            //set initial title in movie detail screen
            val titleText = findViewById<EditText>(R.id.title)
            titleText.setText(movieItem?.title)

            //SET RATING HERE
            //need to add it to xml first
            val starBar = findViewById<RatingBar>(R.id.mhabitStarRating);
            starBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
                when(ratingBar.rating.toInt()){
                    1 -> starBar.rating = 1.0F;
                    2 -> starBar.rating = 2.0F;
                    3 -> starBar.rating = 3.0F;
                    4 -> starBar.rating = 4.0F;
                    5 -> starBar.rating = 5.0F;
                }
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
            var watchagain : Boolean? = null
            val optionList = arrayOf("Yes", "No").toMutableList()

            //initialize spinner
            val spinner = findViewById<Spinner>(R.id.watchagain)
            if (spinner != null) {
                val adapter = ArrayAdapter(this@MovieDetailActivity, android.R.layout.simple_spinner_item, optionList)
                spinner.adapter = adapter
            }

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    //set watchagain according to the index of optionList since there are only two
                    if (position == 0) {
                        watchagain = true
                    } else if (position == 1) {
                        watchagain = false
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //don't need to put anything here for our purposes right now
                }
            }
            //return to home
            findViewById<ImageButton>(R.id.back_to_all).setOnClickListener {
                finish()
            }
            //save edits when button is clicked
            findViewById<Button>(R.id.save).setOnClickListener {

                //update the title
                val newTitle = titleText.text.toString()
                movieItem?.title = newTitle

                //UPDATING THE RATING HERE
                //need to add it to xml first
                val starBar = findViewById<RatingBar>(R.id.mhabitStarRating);
                starBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
                    when(ratingBar.rating.toInt()){
                        1 -> starBar.rating = 1.0F;
                        2 -> starBar.rating = 2.0F;
                        3 -> starBar.rating = 3.0F;
                        4 -> starBar.rating = 4.0F;
                        5 -> starBar.rating = 5.0F;
                    }
                    }

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

                //update watchagain value
                movieItem?.WatchAgain = watchagain


                // get the position of the selected movie item
                val position = intent.getIntExtra("position", -1)
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


        }


