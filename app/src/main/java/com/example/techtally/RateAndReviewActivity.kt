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
    private var percentageOfRatings: Float = 0.0f

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

    private fun calculatePercentageOfRatings(newRating: Int) {
        val sharedPreferences = getSharedPreferences("RatingsPrefs", MODE_PRIVATE)
        val votes1 = sharedPreferences.getInt("VOTES_1", 0)
        val votes2 = sharedPreferences.getInt("VOTES_2", 0)
        val votes3 = sharedPreferences.getInt("VOTES_3", 0)
        val votes4 = sharedPreferences.getInt("VOTES_4", 0)
        val votes5 = sharedPreferences.getInt("VOTES_5", 0)

        when (newRating) {
            1 -> sharedPreferences.edit().putInt("VOTES_1", votes1 + 1).apply()
            2 -> sharedPreferences.edit().putInt("VOTES_2", votes2 + 1).apply()
            3 -> sharedPreferences.edit().putInt("VOTES_3", votes3 + 1).apply()
            4 -> sharedPreferences.edit().putInt("VOTES_4", votes4 + 1).apply()
            5 -> sharedPreferences.edit().putInt("VOTES_5", votes5 + 1).apply()
        }

        val totalVotes = votes1 + votes2 + votes3 + votes4 + votes5 + 1
        val weightedSum = (1 * (votes1 + if (newRating == 1) 1 else 0)) +
                (2 * (votes2 + if (newRating == 2) 1 else 0)) +
                (3 * (votes3 + if (newRating == 3) 1 else 0)) +
                (4 * (votes4 + if (newRating == 4) 1 else 0)) +
                (5 * (votes5 + if (newRating == 5) 1 else 0))

        val percentageOfRatings = if (totalVotes > 0) (weightedSum.toFloat() / totalVotes) else 0f
        sharedPreferences.edit().putFloat("PERCENTAGE_OF_RATINGS", percentageOfRatings).apply()
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

                    val newReview = Review(userName ?: "Guest", rateOfTheUser, commentOfTheUser)


                    // Calculate and save the updated percentage of ratings
                    calculatePercentageOfRatings(rateOfTheUser)

                    // Prepare the intent to send back the result
                    val resultIntent = Intent()
                    resultIntent.putExtra("NEW_RATING", rateOfTheUser) // Pass the new rating
                    setResult(RESULT_OK, resultIntent) // Set the result to OK

                    // Now navigate to SamsungGalaxyS24ReviewsPage
                    val reviewPageIntent = Intent(this@RateAndReviewActivity, SamsungGalaxyS24ReviewsPage::class.java)
                    reviewPageIntent.putExtra("NEW_REVIEW", newReview) // Pass the review object
                    reviewPageIntent.putExtra("PERCENTAGE_OF_RATINGS", percentageOfRatings) // Pass updated percentage
                    reviewPageIntent.putExtra("TOTAL_REVIEWS", getUpdatedNumberOfReviews()) // Pass the total number of reviews
                    startActivity(reviewPageIntent) // Start the review page activity

                    finish() // Close this activity and return to the previous one
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
        return 10
    }

    private fun updateRatingButtons(selectedRating: Int) {
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