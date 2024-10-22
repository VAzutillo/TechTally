package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LaptopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_laptop)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Navigate from LaptopActivity to UserDashboardActivity
        val backToUserDashboardActivity = findViewById<ImageView>(R.id.laptopBackButton)
        backToUserDashboardActivity.setOnClickListener {
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
        }
        val goToMacbookM3ProFullDetailsActivity = findViewById<TextView>(R.id.laptopMacBookM3ProSeeMoreButton)
        goToMacbookM3ProFullDetailsActivity.setOnClickListener {
            val intent = Intent(this, laptopMacbookM3ProFullDetails::class.java)
            startActivity(intent)
        }

    }
}