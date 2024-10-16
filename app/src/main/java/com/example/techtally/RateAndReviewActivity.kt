package com.example.techtally


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RateAndReviewActivity : AppCompatActivity() {
    private var selectedRating: Int = 0 // Declare the selectedRating variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rate_and_review)

        // Retrieve the selected rating from the intent
        selectedRating = intent.getIntExtra("SELECTED_RATING", 0)
        // Retrieve the username from the intent
        val userName = intent.getStringExtra("USER_NAME") ?: "Guest" // Default to "Guest" if null
        Log.d("RateAndReviewActivity", "Selected Rating: $selectedRating, User Name: $userName") // Debug log

        // Set the username to the TextView
        val nameTextView = findViewById<TextView>(R.id.textView12)
        nameTextView.text = userName // Display the username

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Navigate from RateAndReviewActivity to SamsungGalaxyS24FullDetails
        val goToSamsungGalaxyS24FullDetails = findViewById<ImageView>(R.id.rateAndReviewBackButton)
        goToSamsungGalaxyS24FullDetails.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24FullDetails::class.java)
            startActivity(intent)
        }

        // Update the UI based on the selected rating
        updateRatingButtons(selectedRating)

        // Handle the submit button click
        val submitButton = findViewById<Button>(R.id.rateAndReviewSubmitButton)
        submitButton.setOnClickListener {
            submitReview()
        }
    }

    private fun submitReview() {
        // Get user input
        val commentInput = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.rateAndReviewComment)
        val rateOfTheUser = selectedRating // Use selectedRating
        val commentOfTheUser = commentInput.text.toString()

        // Retrieve the username based on the guest status
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isGuest = sharedPreferences.getBoolean("IS_GUEST", false)
        val userName = if (isGuest) "Guest" else sharedPreferences.getString("USER_NAME", null)

        // Create a review request object
        val reviewRequest = ReviewRequest(
            username = userName ?: "Guest",
            rating = rateOfTheUser,
            comment = commentOfTheUser
        )

        RetrofitClient.instance.submitReview(reviewRequest).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                if (response.isSuccessful) {
                    // Create a new Review object with the submitted data
                    val newReview = Review(userName ?: "Guest", rateOfTheUser, commentOfTheUser)

                    val intent = Intent(this@RateAndReviewActivity, SamsungGalaxyS24ReviewsPage::class.java)
                    intent.putExtra("NEW_REVIEW", newReview) // Pass the review
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("RateAndReviewActivity", "Error submitting review: ${response.errorBody()?.string()}")
                    Toast.makeText(this@RateAndReviewActivity, "Failed to submit review: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                Toast.makeText(this@RateAndReviewActivity, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Implement this method to return the updated number of reviews
    private fun getUpdatedNumberOfReviews(): Int {
        // Logic to fetch the updated number of reviews from your database or API
        // This is just a placeholder; implement your own logic to get the total reviews
        return 10 // Replace with actual logic
    }

    private fun updateRatingButtons(selectedRating: Int) {
        // Assuming you have references to your buttons
        val buttons = arrayOf(
            findViewById<Button>(R.id.rateAndReviewButtonTally1),
            findViewById<Button>(R.id.rateAndReviewButtonTally2),
            findViewById<Button>(R.id.rateAndReviewButtonTally3),
            findViewById<Button>(R.id.rateAndReviewButtonTally4),
            findViewById<Button>(R.id.rateAndReviewButtonTally5)
        )

        // Update the button backgrounds based on the selected rating
        for (i in buttons.indices) {
            buttons[i].backgroundTintList = if (i < selectedRating) {
                ContextCompat.getColorStateList(this, R.color.black) // Change to #2F2F2F
            } else {
                ContextCompat.getColorStateList(this, R.color.backgroundColorOfButton) // Reset to default color
            }
        }
    }
}