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
    private lateinit var binding: ActivityLoginBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var searchBar: TextInputEditText
    private lateinit var dropdownLayout: LinearLayout
    private lateinit var greetingTextView: TextView  // Declare once here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_dashboard)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        greetingTextView = findViewById(R.id.greetingTextView)
        searchBar = findViewById(R.id.searchBar)
        dropdownLayout = findViewById(R.id.filterDropdown)

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

        // Handle clicks on the search bar's drawableEnd (i.e., the search icon)
        searchBar.setOnTouchListener { _, event ->
            val drawableEnd = 2 // Position of drawableEnd in compound drawables array

            if (event.action == MotionEvent.ACTION_UP) {
                val drawable = searchBar.compoundDrawables[drawableEnd]
                // Check if the touch event occurred on the drawableEnd (search icon)
                if (drawable != null) {
                    if (event.rawX >= (searchBar.right - drawable.bounds.width())) {
                        // Toggle visibility of dropdown layout
                        dropdownLayout.visibility = if (dropdownLayout.visibility == View.GONE) View.VISIBLE else View.GONE
                        return@setOnTouchListener true  // Handle the touch event
                    }
                }
            }
            false   // Allow other touch events to be processed
        }

        // Handle search bar focus change: show BottomNavigationView only when the search bar is focused
        searchBar.setOnFocusChangeListener { _, hasFocus ->
            bottomNavigationView.visibility = if (hasFocus) View.VISIBLE else View.GONE
        }
    }
}