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
            val intent = Intent(this, laptopAppleMacbookM3ProFullDetails::class.java)
            startActivity(intent)
        }
        val goToSamsungGalaxyBook4SeriesFullDetailsActivity = findViewById<TextView>(R.id.laptopGalaxyBook4SeriesSeeMoreButton)
        goToSamsungGalaxyBook4SeriesFullDetailsActivity.setOnClickListener {
            val intent = Intent(this, laptopSamsungGalaxyBook4SeriesFullDetails::class.java)
            startActivity(intent)
        }
        val goToLenovoLegion7iFullDetailsActivity = findViewById<TextView>(R.id.laptopLenovoLegion7ISeeMoreButton)
        goToLenovoLegion7iFullDetailsActivity.setOnClickListener {
            val intent = Intent(this, laptopLenovoLegion7iFullDetails::class.java)
            startActivity(intent)
        }
        val goToXiaomiNotebookPro120gFullDetailsActivity = findViewById<TextView>(R.id.laptopXiaomiNotebookProSeeMoreButton)
        goToXiaomiNotebookPro120gFullDetailsActivity.setOnClickListener {
            val intent = Intent(this, laptopXiaomiNotebookPro120gFullDetails::class.java)
            startActivity(intent)
        }
        val goToHuaweiMatebook15FullDetailsActivity = findViewById<TextView>(R.id.laptopHuaweiMatebook15SeeMoreButton)
        goToHuaweiMatebook15FullDetailsActivity.setOnClickListener {
            val intent = Intent(this, laptopHuaweiMatebook15FullDetails::class.java)
            startActivity(intent)
        }
        val goToLenovoThinkPadT14sFullDetailsActivity = findViewById<TextView>(R.id.laptopLenovoThinkPadT14sSeeMoreButton)
        goToLenovoThinkPadT14sFullDetailsActivity.setOnClickListener {
            val intent = Intent(this, laptopLenovoThinkPadT14sFullDetails::class.java)
            startActivity(intent)
        }
        val goToAppleM2MacBookPro14FullDetailsActivity = findViewById<TextView>(R.id.laptopAppleM2MacBookPro14SeeMoreButton)
        goToAppleM2MacBookPro14FullDetailsActivity.setOnClickListener {
            val intent = Intent(this, laptopAppleM2MacBookPro14FullDetails::class.java)
            startActivity(intent)
        }
        val goToSamsungGalaxyBook3FullDetailsActivity = findViewById<TextView>(R.id.laptopSamsungGalaxyBook3SeeMoreButton)
        goToSamsungGalaxyBook3FullDetailsActivity.setOnClickListener {
            val intent = Intent(this, laptopSamsungGalaxyBook3FullDetails::class.java)
            startActivity(intent)
        }
        val goToHuaweiMatebookXProFullDetailsActivity = findViewById<TextView>(R.id.laptopHuaweiMatebookXProSeeMoreButton)
        goToHuaweiMatebookXProFullDetailsActivity.setOnClickListener {
            val intent = Intent(this, laptopHuaweiMatebookXProFullDetails::class.java)
            startActivity(intent)
        }
    }
}