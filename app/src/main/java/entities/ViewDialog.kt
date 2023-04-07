package entities

import activities.MainActivity
import adapters.MovieAdapter
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.test.R
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext


class ViewDialog(context: Context) {
    private lateinit var movieDb : MovieDatabase
    var mContext = context


    fun showAddMovieDialog(activity: Activity?) {


        movieDb = MovieDatabase.getDatabase(mContext as MainActivity, GlobalScope)

        val genres = arrayOf("Comedy", "Thriller", "Animated", "Horror", "Romance", "Action", "Other")

        val genreList = genres.toMutableList()

        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.add_movie)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        var genre: Int? = null

        //creating the dropdown box
        val spinner = dialog.findViewById<Spinner>(R.id.genre)
        if (spinner != null) {
            val adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, genreList)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                genre = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

            val doneButton = dialog.findViewById<Button>(R.id.doneAdding)

            //when done button is clicked
            doneButton.setOnClickListener {
                //set the variables to whatever the user input is
                var title = dialog.findViewById<EditText>(R.id.title).text.toString()
                var description = dialog.findViewById<EditText>(R.id.description).text.toString()
                var hours = dialog.findViewById<EditText>(R.id.hours).text.toString()
                var minutes = dialog.findViewById<EditText>(R.id.mins).text.toString()

                //create a new movie object with those variables
                val movie = Movie(
                    title, description, genre, hours.toInt(), minutes.toInt(), 0f, null, null, null
                )

                // add movie to database
                GlobalScope.launch(Dispatchers.IO) {

                movieDb.movieDao().insert(movie)
                }

                dialog.dismiss() //closes the dialog box
                //activity!!.finish() <-- can possibly remove this altogether. it causes the app to crash.
            }
            dialog.show()
        }
    }
