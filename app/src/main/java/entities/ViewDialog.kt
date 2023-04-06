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
import com.example.test.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch

class ViewDialog(context: Context) {
    private lateinit var movieDb : MovieDatabase
    var mContext = context


    fun showAddMovieDialog(activity: Activity?) {

        movieDb = MovieDatabase.getDatabase(mContext as MainActivity)

        val genres = arrayOf("horror", "comedy", "animation")
        val genreList = genres.toMutableList()

        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.add_movie)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        var genre: String? = null

        val spinner = dialog.findViewById<Spinner>(R.id.genre)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                activity,
                android.R.layout.simple_spinner_item, genreList
            )
            spinner.adapter = adapter

        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {

                genre = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


            val doneButton = dialog.findViewById<Button>(R.id.doneAdding)
            doneButton.setOnClickListener {
                var title = dialog.findViewById<EditText>(R.id.title).text.toString()
                var description = dialog.findViewById<EditText>(R.id.description).text.toString()
                var hours = dialog.findViewById<EditText>(R.id.hours).text.toString()
                var minutes = dialog.findViewById<EditText>(R.id.mins).text.toString()
                var seconds = dialog.findViewById<EditText>(R.id.seconds).text.toString()
                val movie = Movie(
                    title, 0.0f, description, 0, minutes.toInt(), seconds.toInt(), hours.toInt(), null, null, null
                )
                GlobalScope.launch(Dispatchers.IO) {
                    movieDb.movieDao().insert(movie)
                }

                dialog.dismiss()
                //activity!!.finish()
            }
            dialog.show()
        }
    }
