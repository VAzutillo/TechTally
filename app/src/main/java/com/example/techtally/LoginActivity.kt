package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.databinding.ActivityLoginBinding

// LoginActivity handles the login functionality and navigation to various activities
class LoginActivity : AppCompatActivity() {

    // Declare variables for view binding and database helper
    private lateinit var binding: ActivityLoginBinding


    // Hardcoded admin credentials for admin login
    private val ADMIN_USERNAME = "admin"
    private val ADMIN_EMAIL = "admin@example.com"
    private val ADMIN_PASSWORD = "admin123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the UserDatabaseHelper for checking user credentials


        // Get the user input by clicking login button
        binding.button2.setOnClickListener {
            // Get user input from login fields
            val loginUserInput = binding.loginUserInput.text.toString()
            val loginPassword = binding.loginPassword.text.toString()

        }

        // Adjust padding for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Navigate from loginPage to MainPage
        // If user doesn't want to log in, he/she can continue as a Guest
        val goToMainPageActivity = findViewById<Button>(R.id.continueAsGuestBtn)
        goToMainPageActivity.setOnClickListener {
            // Start UserDashboardActivity
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
        }

        // Navigate from loginPage to signupPage
        val goToSignup = findViewById<Button>(R.id.button5)
        goToSignup.setOnClickListener {
            // Start SignupActivity
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        // Navigate from loginPage to forgotPasswordPage
        binding.forgotPasswordBtn.setOnClickListener {
            // Start ForgotPasswordActivity
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }



}