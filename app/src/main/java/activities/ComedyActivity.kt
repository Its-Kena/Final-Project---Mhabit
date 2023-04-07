package activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.R
import com.example.test.databinding.ComedyPageBinding
import com.example.test.databinding.MhabitHomeBinding

class ComedyActivity : AppCompatActivity() {
    private lateinit var binding: ComedyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ComedyPageBinding.inflate(layoutInflater)
        setContentView(R.layout.comedy_page)
    }
}