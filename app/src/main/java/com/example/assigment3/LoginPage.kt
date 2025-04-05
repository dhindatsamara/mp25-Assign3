package com.example.assigment3

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginPage : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        sessionManager = SessionManager(this)

        val emailInput = findViewById<EditText>(R.id.etEmail)
        val passwordInput = findViewById<EditText>(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.btnLogin)
        val registerText = findViewById<TextView>(R.id.tvRegister)
        val togglePassword = findViewById<ImageView>(R.id.ivTogglePassword)

        handleIntent(intent)

        if (sessionManager.isLoggedIn()) {
            navigateToLandingPage()
            return
        }

        emailInput.setText(sessionManager.getEmail() ?: "")

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showToast("Format email tidak valid")
                return@setOnClickListener
            }

            val savedEmail = sessionManager.getEmail()
            val savedPassword = sessionManager.getPassword()
            val savedName = sessionManager.getName()

            when {
                savedEmail == null || savedPassword == null || savedName == null -> {
                    showToast("Akun belum terdaftar")
                }
                email != savedEmail -> showToast("Email tidak terdaftar")
                password != savedPassword -> showToast("Password salah")
                else -> {
                    sessionManager.loginUser(savedName, email)
                    navigateToLandingPage()
                }
            }
        }

        registerText.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        togglePassword.setOnClickListener {
            val isVisible = passwordInput.transformationMethod is HideReturnsTransformationMethod
            passwordInput.transformationMethod = if (isVisible)
                PasswordTransformationMethod.getInstance()
            else
                HideReturnsTransformationMethod.getInstance()

            passwordInput.setSelection(passwordInput.text.length)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val name = intent.getStringExtra("REGISTERED_NAME")
        val email = intent.getStringExtra("REGISTERED_EMAIL")
        val password = intent.getStringExtra("REGISTERED_PASSWORD")

        if (!name.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            sessionManager.saveUserData(name, email, password)
            Log.d("LoginPage", "User baru disimpan: $name ($email)")
        }
    }

    private fun navigateToLandingPage() {
        val name = sessionManager.getName() ?: "-"
        val email = sessionManager.getEmail() ?: ""
        val member = Member(name, email)

        val intent = Intent(this, LandingPage::class.java)
        intent.putExtra("MEMBER_DATA", member)
        startActivity(intent)
        finish()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
