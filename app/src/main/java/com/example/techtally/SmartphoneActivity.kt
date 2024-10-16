package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SmartphoneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_smartphone)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Navigate from SmartphoneActivity to UserDashboardActivity
        val backToUserDashboardActivity = findViewById<ImageView>(R.id.smartphoneBackButton)
        backToUserDashboardActivity.setOnClickListener {
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
        }

        // Navigate from SmartphoneActivity to UserDashboardActivity
        //val goToSamsungGalaxyS24FullDetails = findViewById<TextView>(R.id.SmartPhoneSamsungGalaxyS24SeeMoreButton)
       // goToSamsungGalaxyS24FullDetails.setOnClickListener {
         //   val intent = Intent(this, SamsungGalaxyS24FullDetails::class.java)
           // startActivity(intent)
       // }
    }
}