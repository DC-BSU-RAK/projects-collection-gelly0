package com.example.kitface

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class FinalResults : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.final_results)

        val resultImage = findViewById<ImageView>(R.id.resultImage)
        val resultText = findViewById<TextView>(R.id.resultText)
        val backButton = findViewById<Button>(R.id.backButton)

        val character = intent.getIntExtra("characterIndex", 1)
        val clothes = intent.getIntExtra("clothesIndex", 1)
        val accessory = intent.getIntExtra("accessoryIndex", 1)

        val characterName = intent.getStringExtra("characterName") ?: "Character"
        val clothesName = intent.getStringExtra("clothesName") ?: "Clothes"
        val accessoryName = intent.getStringExtra("accessoryName") ?: "Accessory"

        val imageIndex = (character - 1) * 9 + (clothes - 1) * 3 + accessory
        val imageName = "img_$imageIndex"

        val imageResId = resources.getIdentifier(imageName, "drawable", packageName)

        if (imageResId != 0) {
            resultImage.setImageResource(imageResId)
        } else {
            Toast.makeText(this, "Image not found: $imageName", Toast.LENGTH_SHORT).show()
        }

        resultText.text =
            "You chose:\n$characterName\n$clothesName\n$accessoryName"

        backButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}