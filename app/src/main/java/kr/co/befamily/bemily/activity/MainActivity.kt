package kr.co.befamily.bemily.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.befamily.bemily.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}