package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.databinding.ActivityLoginBinding

class MainPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Navigate from MainPageActivity to TopRatedActivity
        val goTopRatedActivity = findViewById<Button>(R.id.topRatedButton)
        goTopRatedActivity.setOnClickListener {
            val intent = Intent(this, TopRatedActivity::class.java)
            startActivity(intent)
        }
        // Navigate from MainPageActivity to LatestActivity
        val goToLatestActivity = findViewById<Button>(R.id.latestButton)
        goToLatestActivity.setOnClickListener {
            val intent = Intent(this, LatestActivity::class.java)
            startActivity(intent)
        }
        // Navigate from MainPageActivity to MostViewedActivity
        val goToMostViewedActivity = findViewById<Button>(R.id.mostViewedButton)
        goToMostViewedActivity.setOnClickListener {
            val intent = Intent(this, MostViewedActivity::class.java)
            startActivity(intent)
        }
        // Navigate from MainPageActivity to ViewAllActivity
        val viewAllButton = findViewById<TextView>(R.id.viewAllButton)
        viewAllButton.setOnClickListener {
            val intent = Intent(this, ViewAllActivity::class.java)
            startActivity(intent)
        }
    }
}