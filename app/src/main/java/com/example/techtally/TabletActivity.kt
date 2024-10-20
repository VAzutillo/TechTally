package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TabletActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tablet)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Navigate from TabletActivity to UserDashboardActivity
        val backToUserDashboardActivity = findViewById<ImageView>(R.id.tabletBackButton)
        backToUserDashboardActivity.setOnClickListener {
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
        }
        // Navigate from TabletActivity to GalaxyTabS10UltraFullDetails
        val GalaxyTabS10UltraSeeMoreBTN = findViewById<TextView>(R.id.tabletGalaxyTabS10UltraSeeMoreButton)
        GalaxyTabS10UltraSeeMoreBTN.setOnClickListener {
            val intent = Intent(this, GalaxyTabS10UltraFullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from TabletActivity to Ipad13ProFullDetails
        val Ipad13ProSeeMoreBTN = findViewById<TextView>(R.id.tabletIpad13ProSeeMoreButton)
        Ipad13ProSeeMoreBTN.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from TabletActivity to OppoPad2FullDetails
        val OppoPad2SeeMoreBTN = findViewById<TextView>(R.id.tabletOppoPad2SeeMoreButton)
        OppoPad2SeeMoreBTN.setOnClickListener {
            val intent = Intent(this, OppoPad2FullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from TabletActivity to RealmePad2FullDetails
        val RealmePad2SeeMoreBTN = findViewById<TextView>(R.id.tabletRealmePad2SeeMoreButton)
        RealmePad2SeeMoreBTN.setOnClickListener {
            val intent = Intent(this, RealmePad2FullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from TabletActivity to XiaomiPad6ProFullDetails
        val XiaomiPad6ProSeeMoreBTN = findViewById<TextView>(R.id.tabletXiaomiPad6ProSeeMoreButton)
        XiaomiPad6ProSeeMoreBTN.setOnClickListener {
            val intent = Intent(this, XiaomiPad6ProFullDetails::class.java)
            startActivity(intent)
        }
    }
}