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
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // Handle the signup button click and call the registerUser function
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
            // Proceed to register the user via API
            else {
                registerUser(signupUsername, signupEmail, signupPassword, signupConfirmPassword) // Pass all required parameters
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

    // Function to register user via API
    private fun registerUser(username: String, email: String, password: String, password_confirmation: String) {
        val request = SignupRequest(username, email, password, password_confirmation) // Include all parameters

        RetrofitClient.api.signup(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Handle successful registration
                    Toast.makeText(this@SignupActivity, "Signup successful", Toast.LENGTH_SHORT).show()
                    // Redirect to another activity if needed
                } else {
                    // Handle API errors
                    Toast.makeText(this@SignupActivity, "Signup failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@SignupActivity, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Function to check if the password contains at least one special character
    private fun containsSpecialCharacter(password: String): Boolean {
        val specialCharacters = "!@#\$&*"
        return password.any { it in specialCharacters }
    }
}