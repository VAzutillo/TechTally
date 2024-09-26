package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.databinding.ActivityLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText

class UserDashboardActivity : AppCompatActivity() {
    // Declare binding, BottomNavigationView, search bar, and dropdown layout
    private lateinit var binding: ActivityLoginBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var searchBar: TextInputEditText
    private lateinit var dropdownLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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
        // Navigate from UserDashboardActivity to Iphone16Activity
        val iPhone16SeeMoreButton = findViewById<TextView>(R.id.SeeMoreBtn1)
        iPhone16SeeMoreButton.setOnClickListener {
            val intent = Intent(this, Iphone16Activity::class.java)
            startActivity(intent)
        }
        // Set up ImageView to navigate to Iphone16Activity when clicked
        val iPhone16Images = findViewById<ImageView>(R.id.iPhone16Image1)
        iPhone16Images.setOnClickListener {
            val intent = Intent(this, Iphone16Activity::class.java)
            startActivity(intent)
        }

        // Get the TextView where the greeting will be displayed
        val greetingTextView = findViewById<TextView>(R.id.welcomeTextView)
        // Retrieve the user's name from the Intent
        val userName = intent.getStringExtra("USER_NAME")

        // Display the appropriate greeting message
        if (userName != null && userName.isNotEmpty()) {
            greetingTextView.text = "Hello, $userName!"
        } else {
            greetingTextView.text = "Hello, User!"
        }

        // Initialize BottomNavigationView and the search bar, along with the dropdown layout for search filters
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        searchBar = findViewById(R.id.searchBar)
        dropdownLayout = findViewById(R.id.filterDropdown)

        // Handle clicks on the search bar's drawableEnd (i.e., the search icon)
        searchBar.setOnTouchListener { e, event ->
            val drawableEnd = 2 // Position of drawableEnd in compound drawables array

            if (event.action == MotionEvent.ACTION_UP) {
                val drawable = searchBar.compoundDrawables[drawableEnd]
                // Check if the touch event occurred on the drawableEnd (search icon)
                if (drawable != null) {
                    if (event.rawX >= (searchBar.right - drawable.bounds.width())) {
                        // If dropdown is currently hidden, show it
                        if (dropdownLayout.visibility == View.GONE) {
                            dropdownLayout.visibility = View.VISIBLE
                        }
                        // Else, if it's visible, hide it
                        else if (dropdownLayout.visibility == View.VISIBLE) {
                            dropdownLayout.visibility = View.GONE
                        }
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

        // Set up BottomNavigationView's item click listener for navigation options
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.action_search) {
                true    // Search action (handled elsewhere)
            } else if (menuItem.itemId == R.id.action_home) {
                startActivity(Intent(this, UserDashboardActivity::class.java))
                true    // Reload UserDashboardActivity
            } else if (menuItem.itemId == R.id.action_back) {
                onBackPressed()     // Navigate back in the activity stack
                true
            } else {
                false       // For any other unhandled cases
            }
        }

        // Remove padding around the icons in BottomNavigationView items
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        for (i in 0 until bottomNavigationView.menu.size()) {
            val item = bottomNavigationView.menu.getItem(i)
            item.icon?.mutate()  // Ensure the drawable is a new instance
            item.icon?.setBounds(0, 0, 0, 0)  // Remove padding
        }
    }
}

