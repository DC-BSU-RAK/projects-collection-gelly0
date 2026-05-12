package com.example.kitface

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AccessoriesActivity : AppCompatActivity() {

    private var selectedAccessoryIndex = 0
    private var selectedAccessoryName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accessories)

        val acc1 = findViewById<ImageView>(R.id.accessories1)
        val acc2 = findViewById<ImageView>(R.id.accessories2)
        val acc3 = findViewById<ImageView>(R.id.accessories3)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val txtSelected = findViewById<TextView>(R.id.txtSelected)

        val characterIndex = intent.getIntExtra("characterIndex", 1)
        val characterName = intent.getStringExtra("characterName")
        val clothesIndex = intent.getIntExtra("clothesIndex", 1)
        val clothesName = intent.getStringExtra("clothesName")

        acc1.setOnClickListener {
            selectedAccessoryIndex = 1
            selectedAccessoryName = "Classic Glasses"
            txtSelected.text = selectedAccessoryName
        }

        acc2.setOnClickListener {
            selectedAccessoryIndex = 2
            selectedAccessoryName = "Beanie"
            txtSelected.text = selectedAccessoryName
        }

        acc3.setOnClickListener {
            selectedAccessoryIndex = 3
            selectedAccessoryName = "Star Glasses"
            txtSelected.text = selectedAccessoryName
        }

        btnNext.setOnClickListener {
            if (selectedAccessoryIndex == 0) {
                Toast.makeText(this, "Please select accessories", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, FinalResults::class.java)

                intent.putExtra("characterIndex", characterIndex)
                intent.putExtra("characterName", characterName)

                intent.putExtra("clothesIndex", clothesIndex)
                intent.putExtra("clothesName", clothesName)

                intent.putExtra("accessoryIndex", selectedAccessoryIndex)
                intent.putExtra("accessoryName", selectedAccessoryName)

                startActivity(intent)
            }
        }
    }
}