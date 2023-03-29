package activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mhabit_home) //this will be changed to the layout name of our main screen once the app is complete. this defines where the user will start upon opening the app.
    }
}