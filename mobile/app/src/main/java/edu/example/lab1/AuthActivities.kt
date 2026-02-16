package edu.example.lab1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authManager = AuthManager(this)

        // If user is already logged in, go to dashboard
        if (authManager.isLoggedIn()) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
            return
        }

        val loginBtn = findViewById<Button>(R.id.btn_login)
        val registerBtn = findViewById<Button>(R.id.btn_register)

        loginBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}

class LoginActivity : AppCompatActivity() {
    private lateinit var authManager: AuthManager
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var switchToRegister: android.widget.TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authManager = AuthManager(this)
        emailInput = findViewById(R.id.et_email)
        passwordInput = findViewById(R.id.et_password)
        loginBtn = findViewById(R.id.btn_login)
        progressBar = findViewById(R.id.progress_bar)
        switchToRegister = findViewById(R.id.tv_switch_to_register)

        loginBtn.setOnClickListener {
            performLogin()
        }

        switchToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun performLogin() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        progressBar.visibility = android.view.View.VISIBLE
        loginBtn.isEnabled = false

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiClient.authApi.login(LoginRequest(email, password))

                if (response.status == "success" && response.data != null) {
                    authManager.saveToken("Bearer ${response.data.token}")
                    authManager.saveUser(response.data.user)
                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = android.view.View.GONE
                loginBtn.isEnabled = true
            }
        }
    }
}

class RegisterActivity : AppCompatActivity() {
    private lateinit var authManager: AuthManager
    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerBtn: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        authManager = AuthManager(this)
        firstNameInput = findViewById(R.id.et_first_name)
        lastNameInput = findViewById(R.id.et_last_name)
        emailInput = findViewById(R.id.et_email)
        passwordInput = findViewById(R.id.et_password)
        registerBtn = findViewById(R.id.btn_register)
        progressBar = findViewById(R.id.progress_bar)

        registerBtn.setOnClickListener {
            performRegister()
        }
    }

    private fun performRegister() {
        val firstName = firstNameInput.text.toString().trim()
        val lastName = lastNameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return
        }

        progressBar.visibility = android.view.View.VISIBLE
        registerBtn.isEnabled = false

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val request = RegisterRequest(firstName, lastName, email, password)
                val response = ApiClient.authApi.register(request)

                if (response.status == "success" && response.data != null) {
                    authManager.saveToken("Bearer ${response.data.token}")
                    authManager.saveUser(response.data.user)
                    Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, DashboardActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = android.view.View.GONE
                registerBtn.isEnabled = true
            }
        }
    }
}
