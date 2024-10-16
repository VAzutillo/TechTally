package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SamsungGalaxyS24ReviewsPage : AppCompatActivity() {

    private lateinit var reviewsRecyclerView: RecyclerView
    private lateinit var reviewsAdapter: ReviewsAdapter
    private val reviewsList = mutableListOf<Review>() // List to hold review data
    private var numberOfReviews: Int = 0 // Variable to hold the number of reviews

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_samsung_galaxy_s24_reviews_page)

        // Find the RecyclerView in activity_reviews_page.xml
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView)

        // Set up the RecyclerView with a LinearLayoutManager
        reviewsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter here to avoid NullPointerException
        reviewsAdapter = ReviewsAdapter(reviewsList)
        reviewsRecyclerView.adapter = reviewsAdapter

        // Retrieve the number of reviews from SharedPreferences
        numberOfReviews = getNumberOfReviews()

        // Check if there's a new review passed from RateAndReviewActivity
        intent.getParcelableExtra<Review>("NEW_REVIEW")?.let { newReview ->
            reviewsList.add(newReview) // Add the new review to the list
            reviewsAdapter.notifyItemInserted(reviewsList.size - 1) // Notify adapter
            onReviewSubmitted() // Call to update number of reviews
        }

        // Fetch reviews from the API
        fetchReviews()

        // Set up back button click listener
        val backButton: ImageView = findViewById(R.id.samsungGalaxyS24ReviewsPageBackButton)
        backButton.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24FullDetails::class.java)
            startActivity(intent) // Start SamsungGalaxyS24FullDetails activity
            finish() // Optionally call finish() to remove the current activity from the back stack
        }
    }

    private fun getNumberOfReviews(): Int {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        return sharedPreferences.getInt("numberOfReviews", 0) // Default to 0 if not found
    }

    private fun fetchReviews() {
        RetrofitClient.apiService.getReviews().enqueue(object : Callback<List<Review>> {
            override fun onResponse(call: Call<List<Review>>, response: Response<List<Review>>) {
                if (response.isSuccessful && response.body() != null) {
                    reviewsList.clear() // Clear existing data
                    reviewsList.addAll(response.body()!!) // Add the fetched reviews

                    // Log the reviews
                    Log.d("SamsungGalaxyS24ReviewsPage", "Fetched Reviews: $reviewsList")

                    // Update the number of reviews
                    numberOfReviews = reviewsList.size // Update the number of reviews
                    saveNumberOfReviews(numberOfReviews) // Save the number of reviews after fetching

                    // Notify the adapter that data has changed
                    reviewsAdapter.notifyDataSetChanged()
                } else {
                    Log.e("SamsungGalaxyS24ReviewsPage", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Review>>, t: Throwable) {
                Log.e("SamsungGalaxyS24ReviewsPage", "Failure: ${t.message}")
            }
        })
    }

    // Function to be called when a new review is submitted
    private fun onReviewSubmitted() {
        // Increment the number of reviews
        numberOfReviews++
        // Save the updated number of reviews to SharedPreferences
        saveNumberOfReviews(numberOfReviews)
    }

    private fun saveNumberOfReviews(count: Int) {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("numberOfReviews", count)
        editor.apply() // Save the changes asynchronously
        Log.d("SamsungGalaxyS24ReviewsPage", "Saved numberOfReviews: $count") // Debug log
    }
}