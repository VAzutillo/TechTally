package com.example.techtally

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.R
import com.example.techtally.databinding.ActivityUserDashboardBinding
import com.google.android.material.textfield.TextInputEditText

class UserDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDashboardBinding
    private lateinit var greetingTextView: TextView
    private lateinit var profilePopup: FrameLayout
    private lateinit var profileBtn2: ImageButton
    private lateinit var profileBtn: ImageButton
    private lateinit var loginSignupBtn: TextView
    private lateinit var filterFrame: FrameLayout
    private lateinit var searchBar: TextInputEditText
    private lateinit var buttonApple: Button
    private lateinit var buttonSamsung: Button
    private lateinit var buttonXiaomi: Button
    private lateinit var buttonRealme: Button
    private lateinit var buttonOppo: Button
    private lateinit var buttonSmartphone: Button
    private lateinit var buttonTablet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        greetingTextView = findViewById(R.id.greetingTextView)
        profilePopup = binding.profilePopup
        profileBtn2 = binding.profileBtn2
        profileBtn = binding.profileBtn
        loginSignupBtn = binding.loginSignupBtn
        filterFrame = findViewById(R.id.filterFrame)
        searchBar = findViewById(R.id.searchBar)
        buttonApple = findViewById(R.id.button_apple)
        buttonSamsung = findViewById(R.id.button_samsung)
        buttonXiaomi = findViewById(R.id.button_xiaomi)
        buttonRealme = findViewById(R.id.button_realme)
        buttonOppo = findViewById(R.id.button_oppo)
        buttonSmartphone = findViewById(R.id.button_smartphone)
        buttonTablet = findViewById(R.id.button_tablet)

        buttonSmartphone.setOnClickListener {
            changeButtonStyle(buttonSmartphone, "#FFFFFF", "#2F2F2F")
            changeButtonStyle(buttonTablet, "#1E1E1E59", "#D9D9D9")
        }

        buttonTablet.setOnClickListener {
            changeButtonStyle(buttonTablet, "#FFFFFF", "#2F2F2F")
            changeButtonStyle(buttonSmartphone, "#1E1E1E59", "#D9D9D9")
        }

        // Set click listeners for all buttons
        val buttons = listOf(buttonApple, buttonSamsung, buttonXiaomi, buttonRealme, buttonOppo)
        buttons.forEach { button ->
            button.setOnClickListener {
                handleButtonClick(button, buttons)
            }
        }

        // Other existing code (navigation, popups, etc.)

        val isGuest = intent.getBooleanExtra("IS_GUEST", false)
        val userName = intent.getStringExtra("USER_NAME")

        // Display appropriate greeting based on user status
        if (isGuest) {
            greetingTextView.text = "Login/Signup"
            greetingTextView.setOnClickListener {
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
            }
        } else if (userName != null && userName.isNotEmpty()) {
            greetingTextView.text = "Hello, $userName!"
        } else {
            greetingTextView.text = "Login/Signup"
        }

        if (isGuest) {
            loginSignupBtn.text = "Login/Signup"
            loginSignupBtn.setOnClickListener {
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
            }
        } else if (userName != null && userName.isNotEmpty()) {
            loginSignupBtn.text = "$userName"
        } else {
            loginSignupBtn.text = "Login/Signup"
        }

        // Show popup when Upload button is clicked
        profileBtn.setOnClickListener {
            profilePopup.visibility = View.VISIBLE
        }

        // Hide popup when Yes or No buttons are clicked
        profileBtn2.setOnClickListener {
            profilePopup.visibility = View.GONE
        }

        // Handle drawable end click to show filter popup
        searchBar.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = searchBar.compoundDrawables[2]
                if (drawableEnd != null) {
                    val bounds: Rect = drawableEnd.bounds
                    val drawableStart = searchBar.width - searchBar.paddingEnd - bounds.width()
                    if (event.x >= drawableStart) {
                        showFilterFrame()
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }

        val backButton = findViewById<Button>(R.id.button_back)
        backButton.setOnClickListener {
            hideFilterFrame()
        }

        // Navigation buttons
        val goTopRatedActivity = findViewById<Button>(R.id.topRatedButton)
        goTopRatedActivity.setOnClickListener {
            val intent = Intent(this, TopRatedActivity::class.java)
            startActivity(intent)
        }

        val goToLatestActivity = findViewById<Button>(R.id.latestButton)
        goToLatestActivity.setOnClickListener {
            val intent = Intent(this, LatestActivity::class.java)
            startActivity(intent)
        }

        val goToMostViewedActivity = findViewById<Button>(R.id.mostViewedButton)
        goToMostViewedActivity.setOnClickListener {
            val intent = Intent(this, MostViewedActivity::class.java)
            startActivity(intent)
        }

        val viewAllButton = findViewById<TextView>(R.id.viewAllButton)
        viewAllButton.setOnClickListener {
            val intent = Intent(this, ViewAllActivity::class.java)
            startActivity(intent)
        }

        binding.samsungGalaxyS24SeeMoreBtn.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24FullDetails::class.java)
            startActivity(intent)
        }
    }

    // Helper function to handle button clicks
    private fun handleButtonClick(selectedButton: Button, allButtons: List<Button>) {
        allButtons.forEach { button ->
            if (button == selectedButton) {
                changeButtonStyle(button, "#FFFFFF", "#2F2F2F") // Selected state
            } else {
                changeButtonStyle(button, "#1E1E1E59", "#D9D9D9") // Default state
            }
        }
    }

    // Helper method to change button style
    private fun changeButtonStyle(button: Button, textColor: String, backgroundColor: String) {
        button.setBackgroundColor(android.graphics.Color.parseColor(backgroundColor))
        button.setTextColor(android.graphics.Color.parseColor(textColor))
    }

    private fun showFilterFrame() {
        filterFrame.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0f, 0f, filterFrame.height.toFloat(), 0f
        )
        animate.duration = 300
        filterFrame.startAnimation(animate)
    }

    private fun hideFilterFrame() {
        val animate = TranslateAnimation(
            0f, 0f, 0f, filterFrame.height.toFloat()
        )
        animate.duration = 300
        filterFrame.startAnimation(animate)
        filterFrame.visibility = View.GONE
    }
}