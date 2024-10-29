package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class laptopLenovoLegion7iFullDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_laptop_lenovo_legion7i_full_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val goToUserDashboardActivity = findViewById<ImageView>(R.id.laptopLenovoLegion7ISeeMoreButton)
        goToUserDashboardActivity.setOnClickListener {
            val intent = Intent(this, LaptopActivity::class.java)
            startActivity(intent)
        }
    }
}