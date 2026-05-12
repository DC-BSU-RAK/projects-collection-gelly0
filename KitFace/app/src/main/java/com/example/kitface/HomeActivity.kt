package com.example.kitface

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnInstructions = findViewById<Button>(R.id.btnInstructions) // ✅ ADD THIS
        val btnChange = findViewById<Button>(R.id.btnChange)

        // Save Name
        val prefs = getSharedPreferences("MyApp", MODE_PRIVATE)
        val name = prefs.getString("USER_NAME", "User")

        tvWelcome.text = "Welcome $name!"

        // Start → go to main
        btnStart.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Instruction Button
        btnInstructions.setOnClickListener {
            startActivity(Intent(this, InstructionsActivity::class.java))
        }

        // Change → back to sign in
        btnChange.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}