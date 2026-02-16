package edu.example.lab1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    private lateinit var authManager: AuthManager

    private lateinit var tvAppTitle: TextView
    private lateinit var profileBtn: Button
    private lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        authManager = AuthManager(this)

        if (!authManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        tvAppTitle = findViewById(R.id.tv_app_title)
        profileBtn = findViewById(R.id.btn_profile)
        logoutBtn = findViewById(R.id.btn_logout)

        val user = authManager.getUser()
        if (user != null) {
            // This is your request:
            tvAppTitle.text = "Hello,${user.firstName}!"
        }

        profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        logoutBtn.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        val dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_confirm_logout, null, false)

        val dialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(true)
                .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setDimAmount(0.65f)

        dialogView.findViewById<Button>(R.id.btnYes).setOnClickListener {
            dialog.dismiss()
            performLogout()
        }

        dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun performLogout() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                ApiClient.authApi.logout()
            } catch (_: Exception) {
                // ignore network error; still clear local session
            } finally {
                authManager.logout()
                Toast.makeText(this@DashboardActivity, "Logged out successfully", Toast.LENGTH_SHORT).show()
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
    private lateinit var genderInput: EditText
    private lateinit var ageInput: EditText
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
        genderInput = findViewById(R.id.et_gender)
        ageInput = findViewById(R.id.et_age)
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
                        genderInput.setText(user.gender ?: "")
                        ageInput.setText(user.age?.toString() ?: "")
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(
                        this@ProfileActivity,
                        "Error loading profile: ${e.message}",
                        Toast.LENGTH_SHORT
                ).show()
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
        val gender = genderInput.text.toString().trim()
        val ageText = ageInput.text.toString().trim()
        val age = if (ageText.isEmpty()) null else try { ageText.toInt() } catch (e: NumberFormatException) { null }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val token = authManager.getToken()
                if (token != null) {
                        val request = UpdateProfileRequest(
                            firstName = firstName,
                            lastName = lastName,
                            email = email,
                            phone = if (phone.isEmpty()) null else phone,
                            gender = if (gender.isEmpty()) null else gender,
                            age = age
                        )

                    val response = ApiClient.authApi.updateProfile(token, request)

                    if (response.status == "success" && response.data != null) {
                        authManager.saveUser(response.data)
                        Toast.makeText(
                                this@ProfileActivity,
                                "Profile updated successfully",
                                Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                                this@ProfileActivity,
                                response.message,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(
                        this@ProfileActivity,
                        "Error updating profile: ${e.message}",
                        Toast.LENGTH_SHORT
                ).show()
            } finally {
                progressBar.visibility = android.view.View.GONE
                saveBtn.isEnabled = true
            }
        }
    }
}
