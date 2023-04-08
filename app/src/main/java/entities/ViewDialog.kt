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
    private lateinit var movieDb: MovieDatabase
    var mContext = context


    fun showAddMovieDialog(activity: Activity?) {


        movieDb = MovieDatabase.getDatabase(mContext as MainActivity, GlobalScope)

        val genres : Array<String> =
            arrayOf("Comedy", "Thriller", "Animated", "Horror", "Romance", "Action", "Other")

        val genreList = genres.toMutableList()

        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.add_movie)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        var genre: String? = null

        //creating the dropdown box
        val spinner = dialog.findViewById<Spinner>(R.id.genre)
        if (spinner != null) {
            val adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, genreList)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                genre = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val doneButton = dialog.findViewById<Button>(R.id.doneAdding)


        var dialogTitle = dialog.findViewById<EditText>(R.id.title)
        var dialogDescription = dialog.findViewById<EditText>(R.id.description)
        var dialogHours = dialog.findViewById<EditText>(R.id.hours)
        var dialogMinutes = dialog.findViewById<EditText>(R.id.mins)


        //when done button is clicked
        doneButton.setOnClickListener {
            //set the variables to whatever the user input is
            var title = dialog.findViewById<EditText>(R.id.title).text.toString()
            var description = dialog.findViewById<EditText>(R.id.description).text.toString()
            var hours = dialog.findViewById<EditText>(R.id.hours).text.toString()
            var minutes = dialog.findViewById<EditText>(R.id.mins).text.toString()

            //disables adding a new movie if any of those fields are empty but the button is no longer usable after this
            //so this is a temporary measure
            if (isEmpty(dialogTitle)|| isEmpty(dialogDescription)  ||
                isEmpty((dialogHours)) || isEmpty(dialogMinutes)
            ) {
                doneButton.isEnabled = false
                doneButton.isClickable = false
            } else {
                //create a new movie object with those variables
                val movie = Movie(
                    title, description, genre, hours.toInt(), minutes.toInt(), 0f, null, null, null
                )


                // add movie to database
                GlobalScope.launch(Dispatchers.IO) {

                    movieDb.movieDao().insert(movie)
                }

                dialog.dismiss() //closes the dialog box
            }
            }

        //cancel button
        val cancelButton = dialog.findViewById<Button>(R.id.cancel)

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun isEmpty(etText: EditText): Boolean {
        return etText.text.toString().trim().isEmpty()
    }
}

