package com.example.techtally

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.databinding.ActivityUserDashboardBinding
import com.google.android.material.textfield.TextInputEditText

class UserDashboardActivity : AppCompatActivity() {
    // View Binding for the layout
    private lateinit var binding: ActivityUserDashboardBinding

    // Declaring UI components
    // For User's profile
    private lateinit var greetingTextView: TextView     // For tvLogin/Signup and For User's name
    private lateinit var profilePopup: FrameLayout      // Layout for profile pop up when click
    private lateinit var profileBtn2: ImageView         // Profile Logo and exit button for pop up
    private lateinit var profileBtn: ImageView          // Profile Logo and to view pop up
    private lateinit var loginSignupBtn: TextView       // Another tvLogin/Signup and get User's name inside the pop up
    // For User's search filter
    private lateinit var filterFrame: FrameLayout       // Layout for search filter pop up
    private lateinit var searchBar: TextInputEditText   // Search bar
    private lateinit var buttonApple: Button            // Apple Button inside the pop up
    private lateinit var buttonSamsung: Button          // Samsung Button inside the pop up
    private lateinit var buttonXiaomi: Button           // Xiaomi Button inside the pop up
    private lateinit var buttonRealme: Button           // Realme Button inside the pop up
    private lateinit var buttonOppo: Button             // Oppo Button inside the pop up
    private lateinit var buttonVivo: Button             // Vivo Button inside the pop up
    private lateinit var buttonHuawei: Button           // Huawei Button inside the pop up
    private lateinit var buttonLenovo: Button           // Lenovo Button inside the pop up
    private lateinit var buttonInfinix: Button          // Infinix Button inside the pop up
    private lateinit var buttonGooglePixel: Button      // GooglePixel Button inside the pop up
    private lateinit var button_back: ImageView         // Back Button inside the pop up
    private lateinit var button_check: ImageView        // check Button inside the pop up
    // For type of device
    private lateinit var buttonSmartphone: Button       // Type of device button inside the pop up
    private lateinit var buttonTablet: Button           // Type of device button inside the pop up
    // For ratings
    private lateinit var buttonTally1: Button           // Rate 1 Tally Button
    private lateinit var buttonTally2: Button           // Rate 2 Tally Button
    private lateinit var buttonTally3: Button           // Rate 3 Tally Button
    private lateinit var buttonTally4: Button           // Rate 4 Tally Button
    private lateinit var buttonTally5: Button           // Rate 5 Tally Button
    // For logout
    private lateinit var logoutBtn: ImageView           // LogoutImageView
    private lateinit var logoutPopup: FrameLayout       // Layout logOutPop-up
    private lateinit var LogoutYesBtn: Button           // Yes button inside pop-up
    private lateinit var LogoutNoBtn: Button            // No button inside pop-up

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enables edge-to-edge display mode
        enableEdgeToEdge()
        // Inflate the layout using view binding
        binding = ActivityUserDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI components
        // For User's profile
        greetingTextView = findViewById(R.id.greetingTextView)      // Initialize tvLogin/Signup and User's name
        profilePopup = binding.profilePopup                         // Initialize Layout profile pop up
        profileBtn2 = binding.profileBtn2                           // Initialize profile button for open pop up
        profileBtn = binding.profileBtn                             // Initialize profile button for close pop up
        loginSignupBtn = binding.loginSignupBtn                     // Initialize tvLoginSignup and User's name inside the pop up
        // For search filter
        // Layout and Brands button
        filterFrame = findViewById(R.id.filterFrame)           // Initialize Layout search filter pop up
        searchBar = findViewById(R.id.searchBar)               // Initialize search bar
        buttonApple = findViewById(R.id.button_apple)          // Initialize Brand Apple button inside the pop up
        buttonSamsung = findViewById(R.id.button_samsung)      // Initialize Brand Samsung button inside the pop up
        buttonXiaomi = findViewById(R.id.button_xiaomi)        // Initialize Brand Xiaomi button inside the pop up
        buttonRealme = findViewById(R.id.button_realme)        // Initialize Brand Realme button inside the pop up
        buttonOppo = findViewById(R.id.button_oppo)            // Initialize Brand Oppo button inside the pop up
        buttonVivo = findViewById(R.id.button_vivo)            // Initialize Brand Vivo button inside the pop up
        buttonHuawei = findViewById(R.id.button_huawei)          // Initialize Brand Huawei button inside the pop up
        buttonLenovo = findViewById(R.id.button_lenovo)          // Initialize Brand Lenovo button inside the pop up
        buttonInfinix = findViewById(R.id.button_infinix)         // Initialize Brand Infinix button inside the pop up
        buttonGooglePixel = findViewById(R.id.button_googlePixel)     // Initialize Brand GooglePixel button inside the pop up
        button_back = findViewById(R.id.button_back)                    // Initialize back button inside the pop up
        button_check = findViewById(R.id.button_check)                  // Initialize Brand check button inside the pop up
        // Type of device button
        buttonSmartphone = findViewById(R.id.button_smartphone)     // Initialize type of device -Smartphone button inside pop up
        buttonTablet = findViewById(R.id.button_tablet)             // Initialize type of device -Tablet button inside pop up
        //  Rating button
        buttonTally1 = findViewById(R.id.Tally1Btn)             // Initialize 1 Tally button
        buttonTally2 = findViewById(R.id.Tally2Btn)             // Initialize 2 Tally button
        buttonTally3 = findViewById(R.id.Tally3Btn)             // Initialize 3 Tally button
        buttonTally4 = findViewById(R.id.Tally4Btn)             // Initialize 4 Tally button
        buttonTally5 = findViewById(R.id.Tally5Btn)             // Initialize 5 Tally button

        logoutBtn = findViewById(R.id.logoutBtn)
        logoutBtn.setOnClickListener {
            showLogoutDialog()
        }

        // Change styles and color when selecting between Smartphone and Tablet filters
        buttonSmartphone.setOnClickListener {
            changeButtonStyle(buttonSmartphone, "#FFFFFF", "#2F2F2F")
            changeButtonStyle(buttonTablet, "#1E1E1E59", "#D9D9D9")
        }

        buttonTablet.setOnClickListener {
            changeButtonStyle(buttonTablet, "#FFFFFF", "#2F2F2F")
            changeButtonStyle(buttonSmartphone, "#1E1E1E59", "#D9D9D9")
        }

        // Set click listeners for Tally rating buttons 1 to 5
        val tally = listOf(buttonTally1, buttonTally2, buttonTally3, buttonTally4, buttonTally5)
        tally.forEach { button ->
            button.setOnClickListener {
                handleTallyClick(button, tally)
            }
        }

        // Set click listeners for Brand buttons
        val buttons = listOf(buttonApple, buttonSamsung, buttonXiaomi, buttonRealme, buttonOppo,
                             buttonVivo, buttonHuawei, buttonLenovo, buttonInfinix, buttonGooglePixel)
        buttons.forEach { button ->
            button.setOnClickListener {
                handleBrandsClick(button, buttons)
            }
        }

        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("IS_LOGGED_IN", false)
        val isGuest = sharedPreferences.getBoolean("IS_GUEST", false) // Retrieve guest status
        val userName = sharedPreferences.getString("USER_NAME", "")

        val greetingTextView: TextView = findViewById(R.id.greetingTextView)
        val loginSignupBtn: TextView = findViewById(R.id.loginSignupBtn)

        if (isGuest) {
            // If the user is a guest, display "Login/Signup"
            greetingTextView.text = "Login/Signup"
            greetingTextView.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            loginSignupBtn.text = "Login/Signup"
            loginSignupBtn.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        } else if (isLoggedIn) {
            // If the user is logged in, show the username
            greetingTextView.text = "Hello, $userName!"
            loginSignupBtn.text = "$userName!"
        } else {
            // Default case, in case guest or login status isn't set
            greetingTextView.text = "Login/Signup"
            greetingTextView.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            loginSignupBtn.text = "Login/Signup"
            loginSignupBtn.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        // Show popup when profile button is clicked
        profileBtn.setOnClickListener {
            profilePopup.visibility = View.VISIBLE
        }

        // Hide popup when profile button2 is clicked
        profileBtn2.setOnClickListener {
            profilePopup.visibility = View.GONE
        }

        // Show search filter popup when the user clicks on the drawableEnd icon in the search bar
        searchBar.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = searchBar.compoundDrawables[2]
                if (drawableEnd != null) {
                    val bounds: Rect = drawableEnd.bounds
                    val drawableStart = searchBar.width - searchBar.paddingEnd - bounds.width()
                    if (event.x >= drawableStart) {
                        showFilterFrame()
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }



        val  clickImage1 = findViewById<ImageView>(R.id.SamsungGalaxyS24Ultra)
        clickImage1.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24UltraFullDetails::class.java)
            startActivity(intent)
        }
        val  clickImage2 = findViewById<ImageView>(R.id.Xiaomi_14_ultra)
        clickImage2.setOnClickListener {
            val intent = Intent(this, Xiaomi14UltraFullDetails::class.java)
            startActivity(intent)
        }
        val  clickImage3 = findViewById<ImageView>(R.id.Iphone_16_Pro_Max)
        clickImage3.setOnClickListener {
            val intent = Intent(this, Iphone16ProMaxFullDetails::class.java)
            startActivity(intent)
        }
        val  clickImage4 = findViewById<ImageView>(R.id.Oppo_Reno_12_Pro)
        clickImage4.setOnClickListener {
            val intent = Intent(this, OppoReno12ProFullDetails::class.java)
            startActivity(intent)
        }
        val  clickImage5 = findViewById<ImageView>(R.id.Realme_13_Pro_Plus)
        clickImage5.setOnClickListener {
            val intent = Intent(this, Realme13ProPlusFullDetails::class.java)
            startActivity(intent)
        }
        /// Navigate from UserDashboardActivity to SamsungGalaxyS24FullDetailsActivity
        val goTopSamsungGalaxyS24FullDetails = findViewById<TextView>(R.id.samsungGalaxyS24SeeMoreButton)
        goTopSamsungGalaxyS24FullDetails.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24FullDetails::class.java)
            intent.putExtra("IS_GUEST", false) // Pass the guest flag
            startActivity(intent)
        }



        // Close the search filter popup when the back button is clicked
        val backButton = findViewById<ImageView>(R.id.button_back)
        backButton.setOnClickListener {
            hideFilterFrame()
        }

        // Navigate from UserDashboardActivity to SmartphoneActivity
        val goTopSmartphoneActivity = findViewById<ImageView>(R.id.smartphonBtn)
        goTopSmartphoneActivity.setOnClickListener {
            val intent = Intent(this, SmartphoneActivity::class.java)
            startActivity(intent)
        }
        // Navigate from UserDashboardActivity to TabletActivity
        val goToLaptopActivity = findViewById<ImageView>(R.id.laptopBtn)
        goToLaptopActivity.setOnClickListener {
            val intent = Intent(this, LaptopActivity::class.java)
            startActivity(intent)
        }
        // Navigate from UserDashboardActivity to LaptopActivity
        val goToTabletActivity = findViewById<ImageView>(R.id.tabletBtn)
        goToTabletActivity.setOnClickListener {
            val intent = Intent(this, TabletActivity::class.java)
            startActivity(intent)
        }
        // Navigate from UserDashboardActivity to ViewAllActivity
        val viewAllButton = findViewById<TextView>(R.id.viewAllButton)
        viewAllButton.setOnClickListener {
            val intent = Intent(this, ViewAllActivity::class.java)
            startActivity(intent)
        }

    }
    private fun showLogoutDialog() {
        // Inflate the custom layout
        val dialogView = layoutInflater.inflate(R.layout.logout_dialog, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView) // Set the custom layout as the dialog view

        // Find buttons in the custom layout
        val logoutYesBtn = dialogView.findViewById<Button>(R.id.LogoutYesBtn)
        val logoutNoBtn = dialogView.findViewById<Button>(R.id.LogoutNoBtn)

        val dialog = builder.create()

        // Set click listener for the Yes button
        logoutYesBtn.setOnClickListener {
            // Clear login information
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("IS_LOGGED_IN", false) // Clear login state
            editor.putString("USER_NAME", null) // Clear username
            editor.apply() // Apply changes

            dialog.dismiss() // Dismiss the dialog

            // Redirect to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Optional: close this activity
        }

        // Set click listener for the No button
        logoutNoBtn.setOnClickListener {
            dialog.dismiss() // Just dismiss the dialog
        }

        dialog.show() // Show the dialog
    }
    override fun onBackPressed() {
        // Show confirmation dialog when back button is pressed
        showExitConfirmationDialog()
    }

    private fun showExitConfirmationDialog() {
        // Inflate the custom layout for the dialog
        val dialogView = layoutInflater.inflate(R.layout.exit_dialog, null)

        // Create an AlertDialog.Builder and set the custom view
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)

        // Create the dialog
        val dialog = builder.create()

        // Get the buttons from the custom layout
        val exitYesButton = dialogView.findViewById<Button>(R.id.ExitYesBtn)
        val exitNoButton = dialogView.findViewById<Button>(R.id.ExitNoBtn)

        // Set click listener for the Yes button
        exitYesButton.setOnClickListener {
            finishAffinity() // Exit the app
            dialog.dismiss()
        }

        // Set click listener for the No button
        exitNoButton.setOnClickListener {
            dialog.dismiss() // Dismiss the dialog
        }

        // Show the dialog
        dialog.show()
    }

    // Method to clear user session data (if applicable)
    private fun clearUserSession() {
        // For example, if you're using SharedPreferences to store user data
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear() // Clear all data
        editor.apply()
    }

    // function to change the appearance of selected and unselected buttons
    private fun handleTallyClick(selectedButton: Button, allButtons: List<Button>) {
        allButtons.forEach { button ->
            if (button == selectedButton) {
                changeButtonStyle(button, "#FFFFFF", "#2F2F2F") // Selected state
            } else {
                changeButtonStyle(button, "#1E1E1E59", "#D9D9D9") // Default state
            }
        }
    }

    // Helper function to handle button clicks
    private fun handleBrandsClick(selectedButton: Button, allButtons: List<Button>) {
        allButtons.forEach { button ->
            if (button == selectedButton) {
                // Change style for the selected button
                changeButtonStyle(button, "#FFFFFF", "#2F2F2F") // Selected state
            } else {
                // Change style for unselected buttons
                changeButtonStyle(button, "#1E1E1E59", "#D9D9D9") // Default state
            }
        }
    }

    // method to change the background color and text color of a button
    private fun changeButtonStyle(button: Button, textColor: String, backgroundColor: String) {
        button.setBackgroundColor(android.graphics.Color.parseColor(backgroundColor))
        button.setTextColor(android.graphics.Color.parseColor(textColor))
    }

    // Show the search filter pop-up with an animation
    private fun showFilterFrame() {
        filterFrame.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0f, 0f, filterFrame.height.toFloat(), 0f
        )
        animate.duration = 300
        filterFrame.startAnimation(animate)
    }
    // Hide the search filter pop-up with an animation
    private fun hideFilterFrame() {
        val animate = TranslateAnimation(
            0f, 0f, 0f, filterFrame.height.toFloat()
        )
        animate.duration = 300
        filterFrame.startAnimation(animate)
        filterFrame.visibility = View.GONE

    }


}