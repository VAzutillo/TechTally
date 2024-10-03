package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.databinding.ActivitySamsungGalaxyS24FullDetailsBinding
import com.example.techtally.databinding.ActivityUserDashboardBinding

class SamsungGalaxyS24FullDetails : AppCompatActivity() {
    private lateinit var binding: ActivitySamsungGalaxyS24FullDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySamsungGalaxyS24FullDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val backToUserDashboard = findViewById<Button>(R.id.SamsungGalaxyS24BackButtonToUserDashboard)
        backToUserDashboard.setOnClickListener {
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
        }
    }
}