package com.example.donutify

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var loadingText: TextView
    private var dots = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        loadingText = findViewById(R.id.loadingText)

        startLoadingAnimation()

        // Go to MainActivity after 3 seconds
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }

    private fun startLoadingAnimation() {
        val runnable = object : Runnable {
            override fun run() {
                dots = (dots + 1) % 4
                loadingText.text = "Loading" + ".".repeat(dots)

                handler.postDelayed(this, 500)
            }
        }
        handler.post(runnable)
    }
}