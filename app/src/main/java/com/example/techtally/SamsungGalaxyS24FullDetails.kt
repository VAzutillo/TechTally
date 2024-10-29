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

    private lateinit var binding: ActivitySamsungGalaxyS24FullDetailsBinding
    private var isGuest: Boolean = true  // This flag determines if the user is a guest or logged in
    private var numberOfReviews: Int = 0 // Track the number of reviews

    // Track whether each button has been clicked
    private var buttonStates = BooleanArray(5) // Track states for five buttons

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

        // Initialize the guest status
        updateGuestStatus()

        // Retrieve the guest status from the intent (if passed from LoginActivity)
        isGuest = intent.getBooleanExtra("IS_GUEST", true)

        // Set up all buttons and TextViews from the binding object
        setupClickListeners()

        // Navigate from UserDashboardActivity to LaptopActivity
        val goToUserDashboardActivity = findViewById<ImageView>(R.id.samsungGalaxyS24FullDetailsBackButton)
        goToUserDashboardActivity.setOnClickListener {
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
        }


        val goToSamsungGalaxyS24ReviewsPage = findViewById<TextView>(R.id.numberOfReviews)
        goToSamsungGalaxyS24ReviewsPage.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24ReviewsPage::class.java)
            startActivity(intent)
        }

        // Get the number of reviews from the intent
        numberOfReviews = getNumberOfReviews()

        // Find the TextView and set the number of reviews
        val numberOfReviewsTextView: TextView = findViewById(R.id.numberOfReviews)
        numberOfReviewsTextView.text = "$numberOfReviews reviews"
    }

    override fun onResume() {
        super.onResume()
        updateGuestStatus() // Refresh the guest status when the activity is resumed
    }

    private fun updateGuestStatus() {
        // Retrieve the guest status from shared preferences
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        isGuest = sharedPreferences.getBoolean("IS_GUEST", true) // Default to true if not found

        val username = sharedPreferences.getString("USERNAME", "") ?: ""
        Log.d("SamsungGalaxyS24FullDetails", "isGuest: $isGuest") // Debug log
    }

    private fun setupClickListeners() {
        // Set click listeners for the buttons (updated IDs)
        binding.samsungGalaxyS24FullDetailsRateButtonTally1.setOnClickListener { handleButtonClick(0, binding.samsungGalaxyS24FullDetailsRateButtonTally1) }
        binding.samsungGalaxyS24FullDetailsRateButtonTally2.setOnClickListener { handleButtonClick(1, binding.samsungGalaxyS24FullDetailsRateButtonTally2) }
        binding.samsungGalaxyS24FullDetailsRateButtonTally3.setOnClickListener { handleButtonClick(2, binding.samsungGalaxyS24FullDetailsRateButtonTally3) }
        binding.samsungGalaxyS24FullDetailsRateButtonTally4.setOnClickListener { handleButtonClick(3, binding.samsungGalaxyS24FullDetailsRateButtonTally4) }
        binding.samsungGalaxyS24FullDetailsRateButtonTally5.setOnClickListener { handleButtonClick(4, binding.samsungGalaxyS24FullDetailsRateButtonTally5) }

        // Write a review button listener
        binding.samsungGalaxyS24FullDetailsWriteAndReviewButton.setOnClickListener { handleTextViewClick() }
    }

    private fun handleTextViewClick() {
        Log.d("SamsungGalaxyS24FullDetails", "Write a review clicked. isGuest: $isGuest")
        if (isGuest) {
            showCustomDialog2() // Show alert dialog for guests
        } else {
            // Get the selected rating from the Write and Review button's tag
            val selectedRating = binding.samsungGalaxyS24FullDetailsWriteAndReviewButton.getTag() as? Int ?: 0

            // Retrieve the username from shared preferences (or your data source)
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val userName = sharedPreferences.getString("USER_NAME", "Guest") ?: "Guest" // Ensure consistent key

            val intent = Intent(this, RateAndReviewActivity::class.java).apply {
                putExtra("SELECTED_RATING", selectedRating) // Pass the selected rating
                putExtra("USER_NAME", userName) // Pass the username (consistent key)
            }
            startActivity(intent) // Navigate to RateAndReviewActivity for logged-in users
        }
    }

    private fun handleButtonClick(index: Int, button: Button) {
        Log.d("SamsungGalaxyS24FullDetails", "Button clicked: $index. isGuest: $isGuest") // Log button clicks
        if (isGuest) {
            // If the user is a guest, show the alert dialog
            showCustomDialog2()
        } else {
            // If the user is logged in, change the background tint of the clicked button and previous buttons
            button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.black) // Change clicked button color to #2F2F2F

            // Update the button states
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
                targetButton?.backgroundTintList = if (buttonStates[i]) {
                    ContextCompat.getColorStateList(this, R.color.black) // Change to #2F2F2F
                } else {
                    ContextCompat.getColorStateList(this, R.color.backgroundColorOfButton) // Reset to default color
                }
            }
            // Store the selected rating
            val selectedRating = index + 1 // Rating from 1 to 5
            binding.samsungGalaxyS24FullDetailsWriteAndReviewButton.setTag(selectedRating) // Store rating in the button's tag
        }
    }

    // Show an alert dialog to guests prompting them to sign up or cancel
    private fun showCustomDialog2() {
        // Inflate the custom layout
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

        // Set a click listener for the Sign Up button
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
        val count = sharedPreferences.getInt("numberOfReviews", 0) // Default to 0 if not found
        Log.d("ReviewCount", "Retrieved numberOfReviews: $count") // Debug log
        return count
    }
}