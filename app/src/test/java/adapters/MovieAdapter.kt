package adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import entities.Movie

class MovieAdapter(dataSet: List<Movie>): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val data = dataSet.toMutableList()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
       // val textView = view.findViewById<TextView>(R.id.term_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.movie_card, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }



}