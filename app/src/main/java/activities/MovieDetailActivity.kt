package activities

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.databinding.ComedyPageBinding
import com.example.test.databinding.MovieDetailActivityBinding
import entities.Movie
import entities.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.Serializable

class MovieDetailActivity() : AppCompatActivity() {

    private lateinit var binding: MovieDetailActivityBinding
    private lateinit var movieDb : MovieDatabase
    private var movieItem : Movie? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieDetailActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.movie_detail_activity)

        val bundle: Bundle? = intent.extras

        bundle?.apply {
            //Serializable Data

            movieItem = getSerializable("movie", Movie::class.java)
            if (movieItem != null) {

                //SET TITLE
                val titleText = findViewById<EditText>(R.id.title)
                titleText.setText(movieItem?.title)

                //SET RATING
                //need to add it to xml first

                //SET DESCRIPTION
                val descriptionText = findViewById<EditText>(R.id.descriptionbox)
                descriptionText.setText(movieItem?.description)

                //SET HOURS
                val hourText = findViewById<EditText>(R.id.hours)
                hourText.setText(movieItem?.hours.toString())

                //SET MINS
                val minText = findViewById<EditText>(R.id.minutes)
                minText.setText(movieItem?.minutes.toString())

                //SET WATCHAGAIN
                var watchagain : Boolean? = null


                //creating the dropdown box
                val options = arrayOf("Yes", "No")

                val optionList = options.toMutableList()
                val spinner = findViewById<Spinner>(R.id.watchagain)
                if (spinner != null) {
                    val adapter = ArrayAdapter(this@MovieDetailActivity, android.R.layout.simple_spinner_item, optionList)
                    spinner.adapter = adapter
                }

                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        if (position == 0) {
                            watchagain = true
                        } else if (position == 1)
                            watchagain = false
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
                //SAVE EDITS
                findViewById<Button>(R.id.save).setOnClickListener {

                    //update title
                    var newTitle = titleText.text.toString()
                    movieItem?.title = newTitle

                    //update rating
                    //need to add it to xml first

                    //update description
                    var newDescription = descriptionText.text.toString()
                    movieItem?.description = newDescription

                    //update hours
                    var newHours = hourText.text.toString().toInt()
                    movieItem?.hours = newHours

                    //update mins
                    var newMins = minText.text.toString().toInt()
                    movieItem?.minutes = newMins

                    //update review
                    val reviewText = findViewById<EditText>(R.id.reviewbox)
                    var review = reviewText.text.toString()
                    movieItem?.review = review

                    //update watchagain
                    movieItem?.WatchAgain = watchagain

                    updateMovie()
                }

            }



        }
        }

        fun updateMovie() {
            //use coroutine to carry out action
            movieDb = MovieDatabase.getDatabase(this, GlobalScope)
            GlobalScope.launch(Dispatchers.IO) {
                movieDb.movieDao().update(movieItem) }
            }
        }


