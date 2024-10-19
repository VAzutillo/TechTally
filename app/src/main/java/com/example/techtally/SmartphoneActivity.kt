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
        val SamsungGalaxyS24UltraSeeMoreBTN = findViewById<TextView>(R.id.smartphoneSamsungGalaxyS24UltraSeeMoreButton)
        SamsungGalaxyS24UltraSeeMoreBTN.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24UltraSeeMoreBTN::class.java)
            startActivity(intent)
        }
        // Navigate from SmartphoneActivity to SamsungGalaxyS24FullDetails
        val goToSamsungGalaxyS24FullDetails = findViewById<TextView>(R.id.smartphoneSamsungGalaxyS24SeeMoreButton)
        goToSamsungGalaxyS24FullDetails.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24FullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from SmartphoneActivity to Iphone16ProMaxFullDetails
        val goToSamsungIphone16ProMaxFullDetails = findViewById<TextView>(R.id.smartphoneIphone16ProMaxSeeMoreButton)
        goToSamsungIphone16ProMaxFullDetails.setOnClickListener {
            val intent = Intent(this, Iphone16ProMaxFullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from SmartphoneActivity to SamsungGalaxyS24UltraFullDetails
        val goToSamsungSamsungGalaxyS24UltraFullDetails = findViewById<TextView>(R.id.smartphoneSamsungGalaxyS24UltraSeeMoreButton)
        goToSamsungSamsungGalaxyS24UltraFullDetails.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24UltraFullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from SmartphoneActivity to VivoX100ProFullDetails
        val goToVivoX100ProFullDetails = findViewById<TextView>(R.id.smartphoneVivoX100ProSeeMoreButton)
            goToVivoX100ProFullDetails.setOnClickListener {
            val intent = Intent(this, VivoX100ProFullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from SmartphoneActivity to Realme13ProPlusFullDetails
        val goToRealme13ProPlusFullDetails = findViewById<TextView>(R.id.smartphoneRealme13ProPlusSeeMoreButton)
        goToRealme13ProPlusFullDetails.setOnClickListener {
            val intent = Intent(this, Realme13ProPlusFullDetails::class.java)
            startActivity(intent)
        }
    }
}