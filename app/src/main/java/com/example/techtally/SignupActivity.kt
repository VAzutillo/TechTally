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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    // Declare a variable for view binding to access views in the layout
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Inflate the layout using view binding
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Check username field if empty it will display the message "Username is required"
        binding.signupUsername.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val username = binding.signupUsername.text.toString()
                if (username.isEmpty()) {
                    binding.usernameHint.text = "Username is required"
                    binding.usernameHint.setTextColor(Color.RED)
                }
            }
        }

        // TextWatcher for password validation length and strength
        binding.signupPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = s?.toString() ?: ""  // Get the password input as a string

                // Validate password length and check for special characters
                if (password.length < 8 || password.length > 16) {
                    // Show error message for invalid length
                    binding.passwordHint.text = "The minimum is 8 characters and maximum of 16 characters"
                    binding.passwordHint.setTextColor(Color.RED)
                    // Resetting the password color to default (optional)
                    binding.signupPassword.setTextColor(Color.BLACK)    // Reset password text color to default
                } else if (!containsSpecialCharacter(password)) {
                    // Show weak password message if it doesn't contain special characters
                    binding.signupPassword.setTextColor(Color.RED) // Red
                    binding.passwordHint.text = getString(R.string.weak_password)
                    binding.passwordHint.setTextColor(Color.RED) // Red
                } else {
                    // Show strong password message if valid
                    binding.signupPassword.setTextColor(Color.parseColor("#32CD32")) // Light green
                    binding.passwordHint.text = getString(R.string.strong_password)
                    binding.passwordHint.setTextColor(Color.parseColor("#32CD32")) // Light green
                }
            }
            // Unused, but required overrides for TextWatcher
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // to check if password field is empty, if empty it will display the message "Password is required"
        binding.signupPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val password = binding.signupPassword.text.toString()
                if (password.isEmpty()) {
                    binding.passwordHint.text = "Password is required"
                    binding.passwordHint.setTextColor(Color.RED)
                }
            }
        }

        // TextWatcher for email validation
        binding.signupEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = s?.toString() ?: ""     // Get the email input as a string
                // Check if the email ends with "@gmail.com"
                if (email.endsWith("@gmail.com")) {
                    binding.emailHint.text = "Valid email"
                    binding.emailHint.setTextColor(Color.parseColor("#32CD32")) // Light green
                } else {
                    binding.emailHint.text = "Invalid email"
                    binding.emailHint.setTextColor(Color.RED) // Red
                }
            }
            // Unused, but required overrides for TextWatcher
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // to check if email field is empty, if empty it will display the message "Email is required"
        binding.signupEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val email = binding.signupEmail.text.toString()
                if (email.isEmpty()) {
                    binding.emailHint.text = "Email is required"
                    binding.emailHint.setTextColor(Color.RED)
                }
            }
        }

        // to validate if confirm password matches the password
        binding.signupConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = binding.signupPassword.text.toString()
                val confirmPassword = s?.toString() ?: ""

                // Check if the confirm password matches the password
                if (password == confirmPassword) {
                    // Set text color to green and show a "password matched" message
                    binding.signupConfirmPassword.setTextColor(Color.parseColor("#32CD32")) // Light green
                    binding.confirmPasswordHint.text = "The password matched"
                    binding.confirmPasswordHint.setTextColor(Color.parseColor("#32CD32")) // Light green
                } else {
                    // Set text color to red and show a "password did not match" message
                    binding.signupConfirmPassword.setTextColor(Color.RED) // Red
                    binding.confirmPasswordHint.text = "Password did not match"
                    binding.confirmPasswordHint.setTextColor(Color.RED) // Red
                }
            }
            // Unused, but required overrides for TextWatcher
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Check if confirm password field is empty, if empty it will display the message "Confirm password is required"
        binding.signupConfirmPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val confirmPassword = binding.signupConfirmPassword.text.toString()
                if (confirmPassword.isEmpty()) {
                    binding.confirmPasswordHint.text = "Confirm password is required"
                    binding.confirmPasswordHint.setTextColor(Color.RED)
                }
            }
        }

        // Handle the signup button click to submit the form
        binding.button3.setOnClickListener {
            val signupUsername = binding.signupUsername.text.toString()
            val signupEmail = binding.signupEmail.text.toString()
            val signupPassword = binding.signupPassword.text.toString()
            val signupConfirmPassword = binding.signupConfirmPassword.text.toString()

            if (signupEmail.isEmpty() || signupPassword.isEmpty() || signupConfirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (signupPassword != signupConfirmPassword) {   // Validate if passwords do not match
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else if (!containsSpecialCharacter(signupPassword)) { // Validate if the password does not contain special characters
                Toast.makeText(this, "Weak password, needs to add special characters (!,@,#,$,&,*)", Toast.LENGTH_SHORT).show()
            } else {    // If everything is valid, proceed with user registration via API
                registerUser(signupUsername, signupEmail, signupPassword, signupConfirmPassword)
            }
        }

        // Set up edge-to-edge padding for immersive display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Navigate from SignupPaqe to LoginPage
        binding.ToLogIn.setOnClickListener {
            // Start SignupActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }



    // Function to register a user by sending the user data to the API using Retrofit
    private fun registerUser(username: String, email: String, password: String, password_confirmation: String) {
        // Create a request object with the user data
        val request = UserSignupRequest(username, email, password, password_confirmation)

        // Make an API call to register the user
        RetrofitClient.instance.registerUser(request).enqueue(object : Callback<SignupResponse> {
            // Handle API response
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if (response.isSuccessful) {
                    // Show a success message to the user
                    Toast.makeText(this@SignupActivity, response.body()?.message ?: "Signup successful", Toast.LENGTH_SHORT).show()

                    // Check if we should navigate to LoginActivity
                if (response.body()?.navigate == true) {
                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                    }
                } else {
                    // Show an error message if signup failed
                    Toast.makeText(this@SignupActivity, "Signup failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }
            // Handle API call failure
            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Function to check if the password contains special characters
    private fun containsSpecialCharacter(password: String): Boolean {
        val specialCharacters = "!@#\$&*"
        return password.any { it in specialCharacters }
    }
}