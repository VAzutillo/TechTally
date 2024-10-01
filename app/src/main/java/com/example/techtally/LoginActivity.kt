package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    // Declare a variable for view binding to access views in the layout
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Enable edge-to-edge display for a more immersive UI
        binding = ActivityLoginBinding.inflate(layoutInflater)  // Inflate the layout using view binding
        setContentView(binding.root)

        // Get the user input by clicking the login button
        binding.button2.setOnClickListener {
            // Get user input from login fields
            val loginUserInput = binding.loginUserInput.text.toString()
            val loginPassword = binding.loginPassword.text.toString()

            // Check if field is empty and show a message
            if (loginUserInput.isEmpty() || loginPassword.isEmpty()) {
                Toast.makeText(this, "Please enter username/email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Create a login request object with the user's input
            val loginRequest = LoginRequest(loginUserInput, loginPassword)
            // Make an API call to login the user using Retrofit
            RetrofitClient.instance.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
                // Handle the response from the API
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        // Get the login response from the server
                        val loginResponse = response.body()

                    // Check if the login was successful and navigate to UserDashboardActivity
                    if (loginResponse != null && loginResponse.success) {
                        // Navigate to UserDashboardActivity
                        startActivity(Intent(this@LoginActivity, UserDashboardActivity::class.java))
                        finish()
                    } else {
                        // Show the error message from the server if login failed
                        Toast.makeText(this@LoginActivity, loginResponse?.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Log the error response for debugging
                        Log.e("LoginActivity", "Error: ${response.errorBody()?.string()}")
                        // Show a toast message indicating incorrect credentials
                        Toast.makeText(this@LoginActivity, "Incorrect Username/Email or Password", Toast.LENGTH_SHORT).show()
                    }
                }
                // Handle the case where the API call fails
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    // Log error for debugging
                    Log.e("LoginActivity", "API call failed: ${t.message}")
                    // Show a toast message indicating the failure
                    Toast.makeText(this@LoginActivity, "Login failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Adjust padding for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Get system bar insets and adjust padding accordingly
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