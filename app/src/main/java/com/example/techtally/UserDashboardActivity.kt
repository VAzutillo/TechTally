package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.databinding.ActivityLoginBinding
import com.example.techtally.databinding.ActivityUserDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText

class UserDashboardActivity : AppCompatActivity() {
    // Declare BottomNavigationView, search bar, and dropdown layout
    private lateinit var binding: ActivityUserDashboardBinding
    private lateinit var greetingTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Initialize binding for the correct layout
        binding = ActivityUserDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        greetingTextView = findViewById(R.id.greetingTextView)

        // Retrieve the intent extras for user info
        val isGuest = intent.getBooleanExtra("IS_GUEST", false)
        val userName = intent.getStringExtra("USER_NAME")

        // Display appropriate greeting based on user status (guest or logged in)
        if (isGuest) {
            greetingTextView.text = "Login/Signup"
            // Make the TextView clickable to navigate to SignupActivity
            greetingTextView.setOnClickListener {
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
            }
        } else if (userName != null && userName.isNotEmpty()) {
            greetingTextView.text = "Hello, $userName!"
        } else {
            greetingTextView.text = "Login/Signup"
        }

        // Navigate from UserDashboardActivity to TopRatedActivity
        val goTopRatedActivity = findViewById<Button>(R.id.topRatedButton)
        goTopRatedActivity.setOnClickListener {
            val intent = Intent(this, TopRatedActivity::class.java)
            startActivity(intent)
        }
        // Navigate from UserDashboardActivity to LatestActivity
        val goToLatestActivity = findViewById<Button>(R.id.latestButton)
        goToLatestActivity.setOnClickListener {
            val intent = Intent(this, LatestActivity::class.java)
            startActivity(intent)
        }
        // Navigate from UserDashboardActivity to MostViewedActivity
        val goToMostViewedActivity = findViewById<Button>(R.id.mostViewedButton)
        goToMostViewedActivity.setOnClickListener {
            val intent = Intent(this, MostViewedActivity::class.java)
            startActivity(intent)
        }
        // Navigate from UserDashboardActivity to ViewAllActivity
        val viewAllButton = findViewById<TextView>(R.id.viewAllButton)
        viewAllButton.setOnClickListener {
            val intent = Intent(this, ViewAllActivity::class.java)
            startActivity(intent)
        }
        binding.samsungGalaxyS24SeeMoreBtn.setOnClickListener {
            // Start ForgotPasswordActivity
            val intent = Intent(this, SamsungGalaxyS24FullDetails::class.java)
            startActivity(intent)
        }
    }
}