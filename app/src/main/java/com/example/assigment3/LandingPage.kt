package com.example.assigment3

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LandingPage : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        sessionManager = SessionManager(this)

        val welcomeText = findViewById<TextView>(R.id.tvWelcome)
        val logoutButton = findViewById<Button>(R.id.btnLogout)

        val member = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("MEMBER_DATA", Member::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("MEMBER_DATA")
        }

        val name = member?.fullName ?: sessionManager.getName() ?: "Guest"
        welcomeText.text = "Hi, Welcome ${name.capitalizeWords()}!\nHave a Nice Day!"

        logoutButton.setOnClickListener {
            sessionManager.logout()
            startActivity(Intent(this, LoginPage::class.java))
            finish()
        }
    }

    private fun String.capitalizeWords(): String =
        trim().split("\\s+".toRegex()) 
            .joinToString(" ") { word ->
                word.lowercase().replaceFirstChar { it.uppercaseChar() }
            }
}
