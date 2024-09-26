package com.example.techtally

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var userDatabaseHelper: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the UserDatabaseHelper
        userDatabaseHelper = UserDatabaseHelper(this)

        // Add a TextWatcher to the password field to validate password input dynamically
        binding.signupPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = s?.toString() ?: ""
                if (containsSpecialCharacter(password)) {
                    // Display "strong password" in light green if password contains special characters
                    binding.signupPassword.setTextColor(Color.parseColor("#32CD32")) // Light green color
                    binding.passwordHint.text = getString(R.string.strong_password)
                    binding.passwordHint.setTextColor(Color.parseColor("#32CD32")) // Light green color
                } else {
                    // Display "password must contain one special symbol" in red if not
                    binding.signupPassword.setTextColor(Color.RED) // Red color
                    binding.passwordHint.text = getString(R.string.weak_password)
                    binding.passwordHint.setTextColor(Color.RED) // Red color
                }
            }
            // Empty methods required by TextWatcher interface
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Add a TextWatcher to the confirm password field to check if passwords match
        binding.signupConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = binding.signupPassword.text.toString()   // Get the original password
                val confirmPassword = s?.toString() ?: ""               // Get the confirm password input

                // Check if passwords match
                if (password == confirmPassword) {
                    // Display "The password matched" in light green if they match
                    binding.signupConfirmPassword.setTextColor(Color.parseColor("#32CD32")) // Light green color
                    binding.confirmPasswordHint.text = "The password matched"
                    binding.confirmPasswordHint.setTextColor(Color.parseColor("#32CD32")) // Light green color
                } else {
                    // Display "Password did not match" in red if they do not match
                    binding.signupConfirmPassword.setTextColor(Color.RED) // Red color
                    binding.confirmPasswordHint.text = "Password did not match"
                    binding.confirmPasswordHint.setTextColor(Color.RED) // Red color
                }
            }
            // Empty methods required by TextWatcher interface
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Get the user input by clicking create account button
        binding.button3.setOnClickListener {
            val signupUsername = binding.signupUsername.text.toString()
            val signupEmail = binding.signupEmail.text.toString()
            val signupPassword = binding.signupPassword.text.toString()
            val signupConfirmPassword = binding.signupConfirmPassword.text.toString()

            // Check if any of the fields are empty
            if (signupUsername.isEmpty() || signupEmail.isEmpty() || signupPassword.isEmpty() || signupConfirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
            // Check if passwords match
            else if (signupPassword != signupConfirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
            // Check if the password contains special characters
            else if (!containsSpecialCharacter(signupPassword)) {
                Toast.makeText(this, "Weak password, needs to add special symbols (!,@,#,$,&,*)", Toast.LENGTH_SHORT).show()
            }
            // Proceed to register the user and save to the database
            else {
                signupDatabase(signupUsername, signupEmail, signupPassword)
            }
        }

        // Set up window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Navigate from signupPage to loginPage
        val goToLogin = findViewById<Button>(R.id.button9)
        goToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to handle saving a new user to the database
    private fun signupDatabase(username: String, email: String, password: String) {
        val insertedRowId = userDatabaseHelper.insertUser(username, email, password)
        if (insertedRowId != -1L) {
            // Show a success message and navigate to the main page
            Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
            finish() // Close the signup activity
        } else {
            // Show a failure message if signup failed
            Toast.makeText(this, "Signup Failed", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to check if the password contains at least one special character
    private fun containsSpecialCharacter(password: String): Boolean {
        val specialCharacters = "!@#\$&*"
        return password.any { it in specialCharacters }
    }
}