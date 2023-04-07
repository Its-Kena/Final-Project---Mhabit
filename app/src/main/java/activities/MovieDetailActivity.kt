package activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.databinding.ComedyPageBinding
import com.example.test.databinding.MovieDetailActivityBinding

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: MovieDetailActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieDetailActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.movie_detail_activity)


        //EDIT REVIEW BUTTON
        findViewById<Button>(R.id.edit).setOnClickListener {
            val reviewText = findViewById<android.widget.EditText>(com.example.test.R.id.reviewbox)
            var review = reviewText.text.toString()
            println(review)
        }
    }

}