package com.example.assigment3

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterPage : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        sessionManager = SessionManager(this)

        val nameInput = findViewById<EditText>(R.id.etName)
        val emailInput = findViewById<EditText>(R.id.etEmail)
        val passwordInput = findViewById<EditText>(R.id.etPassword)
        val confirmPasswordInput = findViewById<EditText>(R.id.etConfirmPassword)
        val registerButton = findViewById<Button>(R.id.btnRegister)
        val togglePassword = findViewById<ImageView>(R.id.ivTogglePassword)
        val toggleConfirmPassword = findViewById<ImageView>(R.id.ivToggleConfirmPassword)
        val backButton = findViewById<ImageView>(R.id.ivBack)

        backButton.setOnClickListener { finish() }

        togglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            passwordInput.transformationMethod =
                if (isPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()
            passwordInput.setSelection(passwordInput.text.length)
        }

        toggleConfirmPassword.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            confirmPasswordInput.transformationMethod =
                if (isConfirmPasswordVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()
            confirmPasswordInput.setSelection(confirmPasswordInput.text.length)
        }

        registerButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirmPassword = confirmPasswordInput.text.toString().trim()

            when {
                name.isEmpty() -> showToast("Nama tidak boleh kosong")
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> showToast("Format email tidak valid")
                password.length < 6 -> showToast("Password minimal 6 karakter")
                password != confirmPassword -> showToast("Konfirmasi password tidak cocok")
                else -> {
                    sessionManager.saveUserData(name, email, password)
                    showToast("Registrasi berhasil!")
                    val intent = Intent(this, LoginPage::class.java).apply {
                        putExtra("REGISTERED_NAME", name)
                        putExtra("REGISTERED_EMAIL", email)
                        putExtra("REGISTERED_PASSWORD", password)
                        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
