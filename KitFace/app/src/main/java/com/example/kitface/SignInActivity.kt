package com.example.kitface

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val etName = findViewById<EditText>(R.id.etName)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnNext = findViewById<Button>(R.id.btnNext)

        btnNext.setOnClickListener {

            val name = etName.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (name.isEmpty()) {
                etName.error = "Name cannot be empty"
                return@setOnClickListener
            }

            if (!name[0].isUpperCase()) {
                etName.error = "Name must start with a capital letter"
                return@setOnClickListener
            }

            if (password.length != 7) {
                etPassword.error = "Password must be exactly 7 characters"
                return@setOnClickListener
            }

            if (!password.matches(Regex("^[a-zA-Z0-9]{7}$"))) {
                etPassword.error = "Only letters and numbers allowed"
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("MyApp", MODE_PRIVATE)
            prefs.edit().putString("USER_NAME", name).apply()

            // Go to Home
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}