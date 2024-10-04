package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    // Hardcoded admin credentials for direct login without registration
    private val adminUsername = "admin"
    private val adminPassword = "admin123"

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

            // Check if admin is logging in with hardcoded credentials
            if (loginUserInput == adminUsername && loginPassword == adminPassword) {
                // Navigate to AdminDashboardActivity for admin users
                val intent = Intent(this@LoginActivity, AdminDashboardActivity::class.java)
                intent.putExtra("ADMIN_NAME", adminUsername) // Pass hardcoded admin name
                startActivity(intent)
                finish()
                return@setOnClickListener
            }

            // Create a login request object with the user's input for regular login
            val loginRequest = LoginRequest(loginUserInput, loginPassword)

            // Make an API call to login the user using Retrofit
            RetrofitClient.instance.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        // Get the login response from the server
                        val loginResponse = response.body()

                        // Check if the login was successful
                        if (loginResponse != null && loginResponse.success) {
                            // Check if the logged-in user is an admin or a regular user
                            when (loginResponse.role) {
                                "admin" -> {
                                    // Navigate to AdminDashboardActivity for admin users
                                    val intent = Intent(this@LoginActivity, AdminDashboardActivity::class.java)
                                    intent.putExtra("ADMIN_NAME", loginUserInput) // Pass username or email
                                    startActivity(intent)
                                    finish()
                                }
                                "user" -> {
                                    // Navigate to UserDashboardActivity for regular users
                                    val intent = Intent(this@LoginActivity, UserDashboardActivity::class.java)
                                    intent.putExtra("USER_NAME", loginUserInput) // Pass username or email
                                    startActivity(intent)
                                    finish()
                                }
                                else -> {
                                    // Handle unexpected roles or errors
                                    Toast.makeText(this@LoginActivity, "Unexpected user role", Toast.LENGTH_SHORT).show()
                                }
                            }
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

        // Handle guest login
        binding.ContinueAsGuestBtn.setOnClickListener {
            val intent = Intent(this, UserDashboardActivity::class.java)
            intent.putExtra("IS_GUEST", true)  // Pass flag for guest login
            startActivity(intent)
        }

        // Navigate from loginPage to forgotPasswordPage
        binding.forgotPasswordBtn.setOnClickListener {
            // Start ForgotPasswordActivity
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        // Navigate from loginPage to SignupPage
        binding.ToSignUp.setOnClickListener {
            // Start SignupActivity
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}