package com.example.techtally

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.techtally.databinding.ActivityAddNewDeviceBinding
import com.google.android.material.textfield.TextInputEditText

class AddNewDeviceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewDeviceBinding
    private lateinit var smartphoneButton: Button
    private lateinit var tabletButton: Button
    private lateinit var brandsButtonText: Button
    private lateinit var brandPopup: FrameLayout
    private lateinit var uploadImageButton: Button
    private lateinit var imageUploadPopup: FrameLayout
    private lateinit var btnUploadFromDevice: Button
    private lateinit var overviewPopup: FrameLayout
    private lateinit var btnContinue: Button

    // New variables for specifications popup
    private lateinit var specificationsPopup: FrameLayout
    private lateinit var specificationsBtn: Button
    private lateinit var backButton: TextView // Change this line
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize UI elements
        smartphoneButton = binding.AddSmarphoneButton
        tabletButton = binding.addTabletButton
        brandsButtonText = binding.brandsButton
        brandPopup = binding.brandPopup
        uploadImageButton = binding.uploadImageBtn
        imageUploadPopup = binding.imageUploadPopup
        btnUploadFromDevice = binding.btnUploadFromDevice
        overviewPopup = binding.popupLayout
        btnContinue = binding.btnContinue


        // Initialize specifications popup elements
        specificationsPopup = binding.specificationsPopup // Make sure to define this in your XML
        specificationsBtn = binding.specificationsBtn // Make sure to define this in your XML
        backButton = binding.backButton // Make sure to define this in your XML
        continueButton = binding.continueButton // Make sure to define this in your XML

        // Show popup when Specifications button is clicked
        specificationsBtn.setOnClickListener {
            specificationsPopup.visibility = View.VISIBLE
        }

        // Hide popup when Back or Continue buttons are clicked
        backButton.setOnClickListener {
            specificationsPopup.visibility = View.GONE
        }

        continueButton.setOnClickListener {
            specificationsPopup.visibility = View.GONE
        }

        // Overview pop-up logic
        binding.etOverview.setOnClickListener {
            showPopup()
        }

        binding.btnContinue.setOnClickListener {
            val inputText = binding.etDeviceOverview.text.toString()
            if (inputText.isNotEmpty()) {
                // Set the input text to etOverview (convert it to String)
                binding.etOverview.setText(inputText)
            }
            // Hide the popup after setting the text
            hidePopup()
        }

        // Image upload button click
        uploadImageButton.setOnClickListener {
            imageUploadPopup.visibility = View.VISIBLE
        }

        btnUploadFromDevice.setOnClickListener {
            imageUploadPopup.visibility = View.GONE
        }

        // Set click listeners for smartphone and tablet buttons
        smartphoneButton.setOnClickListener {
            changeButtonStyle(smartphoneButton, "#FFFFFF", "#2F2F2F")
            changeButtonStyle(tabletButton, "#2F2F2F", "#FFFFFF")
        }

        tabletButton.setOnClickListener {
            changeButtonStyle(tabletButton, "#FFFFFF", "#2F2F2F")
            changeButtonStyle(smartphoneButton, "#2F2F2F", "#FFFFFF")
        }

        // Brand button click
        brandsButtonText.setOnClickListener {
            blurBackground()
            brandPopup.visibility = View.VISIBLE
        }

        // Brand selection buttons logic
        setupBrandSelection()
    }

    private fun setupBrandSelection() {
        val btnApple: Button = findViewById(R.id.btnApple)
        val btnSamsung: Button = findViewById(R.id.btnSamsung)
        val btnXiaomi: Button = findViewById(R.id.btnXiaomi)
        val btnRealme: Button = findViewById(R.id.btnRealme)
        val btnOppo: Button = findViewById(R.id.btnOppo)

        val brandButtons = listOf(btnApple, btnSamsung, btnXiaomi, btnRealme, btnOppo)

        for (button in brandButtons) {
            button.setOnClickListener {
                selectBrand(button.text.toString())
            }
        }
    }

    private fun selectBrand(brand: String) {
        brandsButtonText.text = brand
        brandPopup.visibility = View.GONE
    }

    private fun showPopup() {
        overviewPopup.visibility = View.VISIBLE
    }

    private fun hidePopup() {
        overviewPopup.visibility = View.GONE
    }

    private fun blurBackground() {
        // Add background blur logic if needed
    }

    private fun changeButtonStyle(button: Button, backgroundColor: String, textColor: String) {
        button.setBackgroundColor(Color.parseColor(backgroundColor))
        button.setTextColor(Color.parseColor(textColor))
    }
}