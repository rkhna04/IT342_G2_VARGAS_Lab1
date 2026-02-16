package edu.example.lab1

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {
    private lateinit var authManager: AuthManager
    private lateinit var welcomeText: TextView
    private lateinit var profileBtn: Button
    private lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        authManager = AuthManager(this)

        // Check if user is logged in
        if (!authManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        welcomeText = findViewById(R.id.tv_welcome)
        profileBtn = findViewById(R.id.btn_profile)
        logoutBtn = findViewById(R.id.btn_logout)

        val user = authManager.getUser()
        if (user != null) {
            welcomeText.text = "Welcome, ${user.firstName}!"
        }

        profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        logoutBtn.setOnClickListener {
            showLogoutConfirmation()
        }
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                performLogout()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun performLogout() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                ApiClient.authApi.logout()
                authManager.logout()
                Toast.makeText(this@DashboardActivity, "Logged out successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@DashboardActivity, MainActivity::class.java))
                finish()
            } catch (e: Exception) {
                authManager.logout()
                startActivity(Intent(this@DashboardActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}

class ProfileActivity : AppCompatActivity() {
    private lateinit var authManager: AuthManager
    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var addressInput: EditText
    private lateinit var cityInput: EditText
    private lateinit var countryInput: EditText
    private lateinit var saveBtn: Button
    private lateinit var backBtn: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        authManager = AuthManager(this)

        if (!authManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        initViews()
        loadUserProfile()
    }

    private fun initViews() {
        firstNameInput = findViewById(R.id.et_first_name)
        lastNameInput = findViewById(R.id.et_last_name)
        emailInput = findViewById(R.id.et_email)
        phoneInput = findViewById(R.id.et_phone)
        addressInput = findViewById(R.id.et_address)
        cityInput = findViewById(R.id.et_city)
        countryInput = findViewById(R.id.et_country)
        saveBtn = findViewById(R.id.btn_save)
        backBtn = findViewById(R.id.btn_back)
        progressBar = findViewById(R.id.progress_bar)

        saveBtn.setOnClickListener { updateProfile() }
        backBtn.setOnClickListener { finish() }
    }

    private fun loadUserProfile() {
        progressBar.visibility = android.view.View.VISIBLE

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val token = authManager.getToken()
                if (token != null) {
                    val response = ApiClient.authApi.getProfile(token)

                    if (response.status == "success" && response.data != null) {
                        val user = response.data
                        firstNameInput.setText(user.firstName)
                        lastNameInput.setText(user.lastName)
                        emailInput.setText(user.email)
                        phoneInput.setText(user.phone ?: "")
                        addressInput.setText(user.address ?: "")
                        cityInput.setText(user.city ?: "")
                        countryInput.setText(user.country ?: "")
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "Error loading profile: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = android.view.View.GONE
            }
        }
    }

    private fun updateProfile() {
        progressBar.visibility = android.view.View.VISIBLE
        saveBtn.isEnabled = false

        val firstName = firstNameInput.text.toString().trim()
        val lastName = lastNameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val phone = phoneInput.text.toString().trim()
        val address = addressInput.text.toString().trim()
        val city = cityInput.text.toString().trim()
        val country = countryInput.text.toString().trim()

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val token = authManager.getToken()
                if (token != null) {
                    val request = UpdateProfileRequest(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        phone = if (phone.isEmpty()) null else phone,
                        address = if (address.isEmpty()) null else address,
                        city = if (city.isEmpty()) null else city,
                        country = if (country.isEmpty()) null else country
                    )

                    val response = ApiClient.authApi.updateProfile(token, request)

                    if (response.status == "success" && response.data != null) {
                        authManager.saveUser(response.data)
                        Toast.makeText(this@ProfileActivity, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@ProfileActivity, response.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "Error updating profile: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = android.view.View.GONE
                saveBtn.isEnabled = true
            }
        }
    }
}

// Add missing imports
import android.widget.EditText
import android.widget.ProgressBar
