package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techtally.databinding.ActivitySamsungGalaxyS24FullDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SamsungGalaxyS24FullDetails : AppCompatActivity() {
    // Binding object to access views in the activity's layout
    private lateinit var binding: ActivitySamsungGalaxyS24FullDetailsBinding
    // Variables to track if the user is a guest and the number of reviews
    private var isGuest: Boolean = true  // This flag determines if the user is a guest or logged in
    private var numberOfReviews: Int = 0 // Track the number of reviews

    private lateinit var reviewsRecyclerView: RecyclerView
    private lateinit var reviewsAdapter: ReviewsAdapter
    private val reviewsList = mutableListOf<Review>()

    // to track the clicked state of each rating button (5 buttons total)
    private var buttonStates = BooleanArray(5) // Track states for five buttons

    private var percentageOfRatings: Float = 0.0f

    // Declare vote variables
    private var votes1: Int = 0
    private var votes2: Int = 0
    private var votes3: Int = 0
    private var votes4: Int = 0
    private var votes5: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySamsungGalaxyS24FullDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adjust the view padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the RecyclerView and set the layout manager
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView)
        val layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true // Reverse layout to show newest items at the top
            stackFromEnd = true  // Stack items from the end
        }
        reviewsRecyclerView.layoutManager = layoutManager // Set the layout manager
        reviewsAdapter = ReviewsAdapter(reviewsList)
        reviewsRecyclerView.adapter = reviewsAdapter

        // The rest of your onCreate code...

        // Retrieve the percentage of ratings from shared preferences
        percentageOfRatings = getPercentageOfRatings()

        // Initialize the guest status
        updateGuestStatus()

        // Set up all buttons and TextViews from the binding object
        setupClickListeners()

        // Retrieve the guest status from the intent if passed from LoginActivity
        isGuest = intent.getBooleanExtra("IS_GUEST", true)

        // Retrieve the reviews list from intent
        intent.getParcelableArrayListExtra<Review>("REVIEWS_LIST")?.let { reviews ->
            reviewsList.addAll(0, reviews) // Add the received reviews to the list
            reviewsAdapter.notifyDataSetChanged() // Notify adapter about the changes
            reviewsAdapter.notifyItemInserted(0)
            reviewsRecyclerView.scrollToPosition(0)
        }

        fetchReviews()

        val scrollView: ScrollView = findViewById(R.id.SamsungGalaxyS24FullDetailsScrollView) // Replace with your actual ScrollView ID
        val backToTopTextView: TextView = findViewById(R.id.SamsungS24BackToTop)

        backToTopTextView.setOnClickListener {
            scrollView.scrollTo(0, 0) // Scroll to the top of the ScrollView
        }

        val goToSamsungGalaxyS24SeeAllRatingsAndReviews1 = findViewById<ImageView>(R.id.SamsungS24SeeAllReviews1)
        goToSamsungGalaxyS24SeeAllRatingsAndReviews1.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24ReviewsPage::class.java)
            startActivity(intent)
        }

        val goToSamsungGalaxyS24SeeAllRatingsAndReviews2 = findViewById<TextView>(R.id.SamsungS24SeeAllReviews2)
        goToSamsungGalaxyS24SeeAllRatingsAndReviews2.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24ReviewsPage::class.java)
            startActivity(intent)
        }

        // Navigate back to UserDashboardActivity when back button is clicked
        val backToSamsungGalaxyS24FullDetails = findViewById<ImageView>(R.id.samsungGalaxyS24FullDetailsBackButton)
        backToSamsungGalaxyS24FullDetails.setOnClickListener {
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
        }
        // Navigate to the reviews page when the number of reviews TextView is clicked
        val goToSamsungGalaxyS24ReviewsPage = findViewById<TextView>(R.id.numberOfReviews)
        goToSamsungGalaxyS24ReviewsPage.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24ReviewsPage::class.java)
            startActivity(intent)
        }

        // Retrieve the number of reviews from shared preferences or intent
        numberOfReviews = getNumberOfReviews()
        // Find the TextView and set the number of reviews
        val numberOfReviewsTextView: TextView = findViewById(R.id.numberOfReviews)
        numberOfReviewsTextView.text = "$numberOfReviews reviews"

        calculateAndSavePercentageOfRatings()
    }

    private fun fetchReviews() {
        RetrofitClient.apiService.getReviews().enqueue(object : Callback<List<Review>> {
            override fun onResponse(call: Call<List<Review>>, response: Response<List<Review>>) {
                if (response.isSuccessful && response.body() != null) {
                    reviewsList.clear() // Clear existing data
                    reviewsList.addAll(response.body()!!) // Add the fetched reviews

                    // Log the reviews
                    Log.d("SamsungGalaxyS24FullDetails", "Fetched Reviews: $reviewsList")

                    // Update the number of reviews
                    numberOfReviews = reviewsList.size // Update the number of reviews

                    // Notify the adapter that data has changed
                    reviewsAdapter.notifyDataSetChanged()
                } else {
                    Log.e("SamsungGalaxyS24FullDetails", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Review>>, t: Throwable) {
                Log.e("SamsungGalaxyS24FullDetails", "Failure: ${t.message}")
            }
        })
    }


    // onResume is called when the activity is resumed after being paused
    override fun onResume() {
        super.onResume()
        updateGuestStatus() // Refresh the guest status when the activity is resumed
        calculateAndSavePercentageOfRatings() // Recalculate ratings percentage
    }

    // Method to update the guest status by checking shared preferences
    private fun updateGuestStatus() {
        // Retrieve the guest status from shared preferences
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        isGuest = sharedPreferences.getBoolean("IS_GUEST", true) // Default to true if not found

        // Get the username from shared preferences (if available)
        val username = sharedPreferences.getString("USERNAME", "") ?: ""
        Log.d("SamsungGalaxyS24FullDetails", "isGuest: $isGuest") // Debug log
    }

    private fun setupClickListeners() {
        binding.samsungGalaxyS24FullDetailsRateButtonTally1.setOnClickListener { handleButtonClick(0, binding.samsungGalaxyS24FullDetailsRateButtonTally1) }
        binding.samsungGalaxyS24FullDetailsRateButtonTally2.setOnClickListener { handleButtonClick(1, binding.samsungGalaxyS24FullDetailsRateButtonTally2) }
        binding.samsungGalaxyS24FullDetailsRateButtonTally3.setOnClickListener { handleButtonClick(2, binding.samsungGalaxyS24FullDetailsRateButtonTally3) }
        binding.samsungGalaxyS24FullDetailsRateButtonTally4.setOnClickListener { handleButtonClick(3, binding.samsungGalaxyS24FullDetailsRateButtonTally4) }
        binding.samsungGalaxyS24FullDetailsRateButtonTally5.setOnClickListener { handleButtonClick(4, binding.samsungGalaxyS24FullDetailsRateButtonTally5) }
    }

    private fun handleButtonClick(index: Int, button: Button) {
        Log.d("SamsungGalaxyS24FullDetails", "Button clicked: $index. isGuest: $isGuest")
        if (isGuest) {
            showCustomDialog2()
        } else {
            button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.black)

            for (i in buttonStates.indices) {
                buttonStates[i] = i <= index
                val targetButton = when (i) {
                    0 -> binding.samsungGalaxyS24FullDetailsRateButtonTally1
                    1 -> binding.samsungGalaxyS24FullDetailsRateButtonTally2
                    2 -> binding.samsungGalaxyS24FullDetailsRateButtonTally3
                    3 -> binding.samsungGalaxyS24FullDetailsRateButtonTally4
                    4 -> binding.samsungGalaxyS24FullDetailsRateButtonTally5
                    else -> null
                }
                targetButton?.backgroundTintList = if (buttonStates[i]) {
                    ContextCompat.getColorStateList(this, R.color.black)
                } else {
                    ContextCompat.getColorStateList(this, R.color.backgroundColorOfButton)
                }
            }
            val selectedRating = index + 1
            saveVotesCount(selectedRating, getVotesCount(selectedRating) + 1)

            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val userName = sharedPreferences.getString("USER_NAME", "Guest") ?: "Guest"

            val intent = Intent(this, RateAndReviewActivity::class.java).apply {
                putExtra("SELECTED_RATING", selectedRating)
                putExtra("USER_NAME", userName)
            }
            startActivityForResult(intent, REQUEST_CODE_REVIEW)
        }
    }

    // Save the votes count and number of reviews when a new review is submitted
    private fun updateReviewCounts() {
        numberOfReviews += 1 // Increment number of reviews
        saveNumberOfReviews(numberOfReviews) // Save to SharedPreferences
        val numberOfReviewsTextView: TextView = findViewById(R.id.numberOfReviews)
        numberOfReviewsTextView.text = "$numberOfReviews reviews" // Update the TextView
    }

    private fun savePercentageOfRatings(percentage: Float) {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("percentageOfRatings", percentage)
        editor.apply()
        Log.d("RatingPercentage", "Saved percentageOfRatings: $percentage")
    }

    // Method to retrieve the percentage of ratings from SharedPreferences
    private fun getPercentageOfRatings(): Float {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        return sharedPreferences.getFloat("percentageOfRatings", 0.0f)
    }

    // Call this method when you calculate the ratings based on the formula
    private fun calculateAndSavePercentageOfRatings() {

        // Retrieve the latest votes counts
        votes1 = getVotesCount(1)
        votes2 = getVotesCount(2)
        votes3 = getVotesCount(3)
        votes4 = getVotesCount(4)
        votes5 = getVotesCount(5)

        val totalVotes = votes1 + votes2 + votes3 + votes4 + votes5
        if (totalVotes > 0) {
            percentageOfRatings = ((5 * votes5) + (4 * votes4) + (3 * votes3) + (2 * votes2) + (1 * votes1)) / totalVotes.toFloat()
            savePercentageOfRatings(percentageOfRatings)
        } else {
            percentageOfRatings = 0.0f
        }
        val percentageOfRatingsTextView: TextView = findViewById(R.id.percentageOfRatings)
        percentageOfRatingsTextView.text = String.format("%.2f", percentageOfRatings)
    }

    // Method to get the votes count based on rating (1 to 5)
    private fun getVotesCount(rating: Int): Int {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        return sharedPreferences.getInt("votes$rating", 0) // Default to 0 if not found
    }

    // Method to save the votes count
    private fun saveVotesCount(rating: Int, count: Int) {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("votes$rating", count)
        editor.apply()
        Log.d("VotesCount", "Saved votes$count for rating $rating") // Debug log
    }

    // Show an alert dialog for guests, prompting them to sign up or cancel
    private fun showCustomDialog2() {
        // Inflate the custom layout for the dialog
        val dialogView = layoutInflater.inflate(R.layout.dialog_custom2, null)

        // Create the AlertDialog with the custom view
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)

        // Get references to the views in the custom layout
        val dialogMessage: TextView = dialogView.findViewById(R.id.dialogMessage)
        val cancelButton: Button = dialogView.findViewById(R.id.LogoutNoBtn)
        val signUpButton: Button = dialogView.findViewById(R.id.LogoutYesBtn)

        // Set the dialog message
        dialogMessage.text = "To continue this activity you need an account first."

        // Create the dialog instance
        val dialog = builder.create()

        // Set click listener for the Sign Up button (navigates to SignupActivity)
        signUpButton.setOnClickListener {
            // Start the SignupActivity when the button is clicked
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            dialog.dismiss()  // Dismiss the dialog after starting the activity
        }

        // Set a click listener for the Cancel button
        cancelButton.setOnClickListener {
            // Dismiss the dialog when Cancel is clicked
            dialog.dismiss()
        }

        // Show the dialog
        dialog.show()
    }

    private fun saveNumberOfReviews(count: Int) {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("numberOfReviews", count)
        editor.apply()
        Log.d("ReviewCount", "Saved numberOfReviews: $count") // Debug log
    }

    private fun getNumberOfReviews(): Int {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        return sharedPreferences.getInt("numberOfReviews", 0)
    }
    companion object {
        private const val REQUEST_CODE_REVIEW = 1
    }
}