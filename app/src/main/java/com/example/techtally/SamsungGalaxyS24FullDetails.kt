package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.databinding.ActivitySamsungGalaxyS24FullDetailsBinding

class SamsungGalaxyS24FullDetails : AppCompatActivity() {
    // Binding object to access views in the activity's layout
    private lateinit var binding: ActivitySamsungGalaxyS24FullDetailsBinding
    // Variables to track if the user is a guest and the number of reviews
    private var isGuest: Boolean = true  // This flag determines if the user is a guest or logged in
    private var numberOfReviews: Int = 0 // Track the number of reviews

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

        // Retrieve the percentage of ratings from shared preferences
        percentageOfRatings = getPercentageOfRatings()

        // Initialize the guest status
        updateGuestStatus()

        // Set up all buttons and TextViews from the binding object
        setupClickListeners()

        // Retrieve the guest status from the intent if passed from LoginActivity
        isGuest = intent.getBooleanExtra("IS_GUEST", true)

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

    // Set up click listeners for all relevant buttons and views
    private fun setupClickListeners() {
        // Set click listeners for the buttons (updated IDs)
        binding.samsungGalaxyS24FullDetailsRateButtonTally1.setOnClickListener { handleButtonClick(0, binding.samsungGalaxyS24FullDetailsRateButtonTally1) }
        binding.samsungGalaxyS24FullDetailsRateButtonTally2.setOnClickListener { handleButtonClick(1, binding.samsungGalaxyS24FullDetailsRateButtonTally2) }
        binding.samsungGalaxyS24FullDetailsRateButtonTally3.setOnClickListener { handleButtonClick(2, binding.samsungGalaxyS24FullDetailsRateButtonTally3) }
        binding.samsungGalaxyS24FullDetailsRateButtonTally4.setOnClickListener { handleButtonClick(3, binding.samsungGalaxyS24FullDetailsRateButtonTally4) }
        binding.samsungGalaxyS24FullDetailsRateButtonTally5.setOnClickListener { handleButtonClick(4, binding.samsungGalaxyS24FullDetailsRateButtonTally5) }

        // Set click listener for the 'Write a review' button
        binding.samsungGalaxyS24FullDetailsWriteAndReviewButton.setOnClickListener { handleTextViewClick() }
    }

    // Handle the 'Write a review' button click
    private fun handleTextViewClick() {
        Log.d("SamsungGalaxyS24FullDetails", "Write a review clicked. isGuest: $isGuest")
        if (isGuest) {
            // Show a custom dialog if the user is a guest
            showCustomDialog2()
        } else {
            // Get the selected rating from the Write and Review button's tag
            val selectedRating = binding.samsungGalaxyS24FullDetailsWriteAndReviewButton.getTag() as? Int ?: 0

            // Retrieve the username from shared preferences
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val userName = sharedPreferences.getString("USER_NAME", "Guest") ?: "Guest"

            // Navigate to the RateAndReviewActivity, passing the selected rating and username
            val intent = Intent(this, RateAndReviewActivity::class.java).apply {
                putExtra("SELECTED_RATING", selectedRating) // Pass the selected rating
                putExtra("USER_NAME", userName) // Pass the username (consistent key)
            }
            startActivityForResult(intent, REQUEST_CODE_REVIEW) // Use startActivityForResult to get back data
        }
    }

    // Handle rating button clicks and change button colors accordingly
    private fun handleButtonClick(index: Int, button: Button) {
        Log.d("SamsungGalaxyS24FullDetails", "Button clicked: $index. isGuest: $isGuest") // Log button clicks
        if (isGuest) {
            // Show alert dialog if the user is a guest
            showCustomDialog2()
        } else {
            // If the user is logged in, change the background tint of the clicked button and previous buttons
            button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.black) // Change clicked button color to #2F2F2F

            // Update the button states (clicked or not) and change background color
            for (i in buttonStates.indices) {
                buttonStates[i] = i <= index // Set button states based on the clicked button
                val targetButton = when (i) {
                    0 -> binding.samsungGalaxyS24FullDetailsRateButtonTally1
                    1 -> binding.samsungGalaxyS24FullDetailsRateButtonTally2
                    2 -> binding.samsungGalaxyS24FullDetailsRateButtonTally3
                    3 -> binding.samsungGalaxyS24FullDetailsRateButtonTally4
                    4 -> binding.samsungGalaxyS24FullDetailsRateButtonTally5
                    else -> null
                }
                // Change the button color based on whether it has been clicked or not
                targetButton?.backgroundTintList = if (buttonStates[i]) {
                    ContextCompat.getColorStateList(this, R.color.black) // Change to #2F2F2F
                } else {
                    ContextCompat.getColorStateList(this, R.color.backgroundColorOfButton) // Reset to default color
                }
            }
            // Store the selected rating in the button's tag
            val selectedRating = index + 1 // Rating from 1 to 5
            binding.samsungGalaxyS24FullDetailsWriteAndReviewButton.setTag(selectedRating) // Store rating in the button's tag
            // Save the vote count for the selected rating
            saveVotesCount(selectedRating, getVotesCount(selectedRating) + 1)

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