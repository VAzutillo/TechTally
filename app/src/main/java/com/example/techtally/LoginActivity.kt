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
    private lateinit var userDatabaseHelper: UserDatabaseHelper

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
        userDatabaseHelper = UserDatabaseHelper(this)

        // Get the user input by clicking login button
        binding.button2.setOnClickListener {
            // Get user input from login fields
            val loginUserInput = binding.loginUserInput.text.toString()
            val loginPassword = binding.loginPassword.text.toString()

            // Check if the input fields are empty and show a message if they are
            if (loginUserInput.isEmpty() || loginPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if ((loginUserInput == ADMIN_EMAIL || loginUserInput == ADMIN_USERNAME) && loginPassword == ADMIN_PASSWORD) {
                // Admin login successful, navigate to AdminDashboardActivity
                Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AdminDashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else if (Patterns.EMAIL_ADDRESS.matcher(loginUserInput).matches()) {
                // If the input is an email, use the loginDatabase method to check loginDatabase
                loginDatabase(null, loginUserInput, loginPassword, loginUserInput)
            } else {
                // If it's not an email, assume it's a username and check loginDatabase
                loginDatabase(loginUserInput, null, loginPassword, loginUserInput)
            }
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

    // Function to check if the user exists in the database
    private fun loginDatabase(username: String?, email: String?, password: String, loginUserInput: String) {
        val userExists: Boolean = if (username != null) {
            // Check if the user exists in the database based on username
            userDatabaseHelper.readUser(username, password)
        } else if (email != null) {
            // If an email is provided, check if it exists with the user input password
            userDatabaseHelper.readUserByEmail(email, password)
        } else {
            // No valid identifier provided
            false
        }
        // Show appropriate message based on whether the user exists
        if (userExists) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            // Start UserDashboardActivity and close the current activity
            val intent = Intent(this, UserDashboardActivity::class.java)
            intent.putExtra("USER_NAME", loginUserInput)  // Pass the user's name
            startActivity(intent)
            finish()
        } else {
            // Show message if account doesn't exist
            Toast.makeText(this, "Account doesn't exist", Toast.LENGTH_SHORT).show()
        }
    }
}