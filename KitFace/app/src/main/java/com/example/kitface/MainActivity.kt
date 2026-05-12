package com.example.kitface

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var selectedCharacterIndex = 0
    private var selectedCharacterName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val char1 = findViewById<ImageView>(R.id.character1)
        val char2 = findViewById<ImageView>(R.id.character2)
        val char3 = findViewById<ImageView>(R.id.character3)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val txtSelected = findViewById<TextView>(R.id.txtSelected)

        char1.setOnClickListener {
            selectedCharacterIndex = 1
            selectedCharacterName = "Alice"
            txtSelected.text = selectedCharacterName
        }

        char2.setOnClickListener {
            selectedCharacterIndex = 2
            selectedCharacterName = "John"
            txtSelected.text = selectedCharacterName
        }

        char3.setOnClickListener {
            selectedCharacterIndex = 3
            selectedCharacterName = "Anna"
            txtSelected.text = selectedCharacterName
        }

        btnNext.setOnClickListener {
            if (selectedCharacterIndex == 0) {
                Toast.makeText(this, "Please select a character", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, ClothesActivity::class.java)
                intent.putExtra("characterIndex", selectedCharacterIndex)
                intent.putExtra("characterName", selectedCharacterName)
                startActivity(intent)
            }
        }
    }
}