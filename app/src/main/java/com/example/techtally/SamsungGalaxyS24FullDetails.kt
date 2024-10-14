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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.databinding.ActivitySamsungGalaxyS24FullDetailsBinding

class SamsungGalaxyS24FullDetails : AppCompatActivity() {

    private lateinit var binding: ActivitySamsungGalaxyS24FullDetailsBinding
    private var isGuest: Boolean = true  // This flag determines if the user is a guest or logged in

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
    }

    override fun onResume() {
        super.onResume()
        updateGuestStatus() // Refresh the guest status when the activity is resumed
    }

    private fun updateGuestStatus() {
        // Retrieve the guest status from shared preferences
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        isGuest = sharedPreferences.getBoolean("IS_GUEST", true) // Default to true if not found
        Log.d("SamsungGalaxyS24FullDetails", "isGuest: $isGuest") // Debug log
    }

    private fun setupClickListeners() {
        // Set click listeners for all relevant buttons and views
        binding.percentageOfRatings.setOnClickListener { handleButtonClick() }
        binding.percentageLogo.setOnClickListener { handleButtonClick() }
        binding.numberOfReviews.setOnClickListener { handleButtonClick() }

        // Button tally click listeners
        binding.rateThisDeviceTally1.setOnClickListener { handleButtonClick() }
        binding.rateThisDeviceTally2.setOnClickListener { handleButtonClick() }
        binding.rateThisDeviceTally3.setOnClickListener { handleButtonClick() }
        binding.rateThisDeviceTally4.setOnClickListener { handleButtonClick() }
        binding.rateThisDeviceTally5.setOnClickListener { handleButtonClick() }

        // Write a review click listener
        binding.writeAReviewButton.setOnClickListener { handleButtonClick() }
    }

    private fun handleButtonClick() {
        Log.d("SamsungGalaxyS24FullDetails", "Button clicked. isGuest: $isGuest") // Log button clicks
        if (isGuest) {
            showCustomDialog2()
        } else {
            // Navigate to RateAndReviewActivity for logged-in users
            val intent = Intent(this, RateAndReviewActivity::class.java)
            startActivity(intent)
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
}