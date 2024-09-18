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

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflate the layout for this activity
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the DatabaseHelper for checking user credentials
        databaseHelper = DatabaseHelper(this)

        // Get the user input by clicking login button
        //check if user account exist if not it will not login
        binding.button2.setOnClickListener {
            // Get user input from login fields
            val loginUserInput = binding.loginUserInput.text.toString()
            val loginPassword = binding.loginPassword.text.toString()

            // Check if any input field is empty
            if (loginUserInput.isEmpty() || loginPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Check if the input is an email address
                if (Patterns.EMAIL_ADDRESS.matcher(loginUserInput).matches()) {
                    // If it's an email, use the loginDatabase method to check loginDatabase
                    loginDatabase(null, loginUserInput, loginPassword)
                } else {
                    // If it's not an email, assume it's a username and check loginDatabase
                    loginDatabase(loginUserInput, null, loginPassword)
                }
        }
        }

        // Adjust padding for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Navigate from loginPage to MainPage
        // If user don't want to login he/she can continue as a Guest
        val goToMainPageActivity = findViewById<Button>(R.id.continueAsGuestBtn)
        goToMainPageActivity.setOnClickListener {
            // Start MainPageActivity
            val intent = Intent(this, MainPageActivity::class.java)
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
    private fun loginDatabase(username: String?, email: String?, password: String) {
        val userExists: Boolean = when {
            // If a username is provided, check if it exists with the user input password
            username != null -> databaseHelper.readUser(username, password)
            // If an email is provided, check if it exists with the user input password
            email != null -> databaseHelper.readUserByEmail(email, password)
            else -> false // No valid identifier provided
        }

        // Show appropriate message based on whether the user exists
        if (userExists) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            // Start MainPageActivity and close the current activity
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // Show message if account doesn't exist
            Toast.makeText(this, "Account doesn't exist", Toast.LENGTH_SHORT).show()
        }
    }
}