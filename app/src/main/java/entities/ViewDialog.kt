package entities

import activities.MainActivity
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.test.R
import kotlinx.coroutines.*


class ViewDialog(context: Context) {
    private lateinit var movieDb: MovieDatabase
    var mContext = context


    fun showAddMovieDialog(activity: Activity?) {
        //initialize the movie database
        movieDb = MovieDatabase.getDatabase(mContext as MainActivity, GlobalScope)

        //create list of genres for user to choose from
        val genreList =
            arrayOf("Comedy", "Thriller", "Animated", "Horror", "Romance", "Action", "Other")
                .toMutableList()

        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.add_movie)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //set initial genre value and create the dropdown box for user to choose one
        var genre: String? = null

        //initialize spinner
        val spinner = dialog.findViewById<Spinner>(R.id.genre)
        if (spinner != null) {
            val adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, genreList)
            spinner.adapter = adapter
        }

        //setting flags to account for spinner selecting first item on first launch
        var a = 1
        var b = 0
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (b < a) {
                    b++
                } else {
                    //set genre to whatever item the user chose
                    genre = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //it isn't necessary to put something here
            }
        }

        ////grab each of the views from this dialog that we want to use
        val dialogTitle = dialog.findViewById<EditText>(R.id.title)
        val dialogDescription = dialog.findViewById<EditText>(R.id.description)
        val dialogHours = dialog.findViewById<EditText>(R.id.hours)
        val dialogMinutes = dialog.findViewById<EditText>(R.id.mins)
        val doneButton = dialog.findViewById<Button>(R.id.doneAdding)

        //when the confirm/done button is clicked
        doneButton.setOnClickListener {
            //set the variables to whatever the user input is
            val title = dialogTitle.text.toString()
            val description = dialogDescription.text.toString()
            val hours = dialogHours.text.toString()
            val minutes = dialogMinutes.text.toString()

            //disables adding a new movie if any of those fields are empty
            //BUT the button is no longer usable after this -- even if the user fills everything in
            //so this is a temporary measure
            if (isEmpty(dialogTitle)|| isEmpty(dialogDescription) || isEmpty((dialogHours)) || isEmpty(dialogMinutes) || genre == null) {
                doneButton.isEnabled = false
                doneButton.isClickable = false
                doneButton.isEnabled = true
                doneButton.isClickable = true
                if (isEmpty(dialogTitle)) {
                    dialogTitle.error = "A title is required!"
                }
                if (isEmpty(dialogDescription)) {
                    dialogDescription.error = "A description is required!"
                }
                if (isEmpty(dialogHours)) {
                    dialogHours.error = "A time is required!"
                }
                if (isEmpty(dialogMinutes)) {
                    dialogMinutes.error = "A time is required!"
                }
                if (genre == null) {
                    Toast.makeText(dialog.context, "A genre is required!", Toast.LENGTH_SHORT).show();
                }

            } else {
                //a new movie object is created with the information the user entered
                val movie = Movie(
                    title, description, genre, hours.toInt(), minutes.toInt(), 0f, null, null, null
                )

                //the new movie is added to the database
                GlobalScope.launch(Dispatchers.IO) {
                    movieDb.movieDao().insert(movie)
                }

                //the dialog box closes
                dialog.dismiss()
            }
        }

        //allows the user to close the dialog box at any time
        val cancelButton = dialog.findViewById<Button>(R.id.cancel)
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    //helper method to check if the content of an EditText view is empty
    private fun isEmpty(editText: EditText): Boolean {
        return editText.text.toString().trim().isEmpty()
    }
}

