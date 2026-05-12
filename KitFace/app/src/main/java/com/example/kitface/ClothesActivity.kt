package com.example.kitface

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ClothesActivity : AppCompatActivity() {

    private var selectedClothesIndex = 0
    private var selectedClothesName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clothes)

        val clothes1 = findViewById<ImageView>(R.id.clothes1)
        val clothes2 = findViewById<ImageView>(R.id.clothes2)
        val clothes3 = findViewById<ImageView>(R.id.clothes3)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val txtSelected = findViewById<TextView>(R.id.txtSelected)

        val characterIndex = intent.getIntExtra("characterIndex", 1)
        val characterName = intent.getStringExtra("characterName")

        clothes1.setOnClickListener {
            selectedClothesIndex = 1
            selectedClothesName = "Blue Tshirt"
            txtSelected.text = selectedClothesName
        }

        clothes2.setOnClickListener {
            selectedClothesIndex = 2
            selectedClothesName = "Red Stripes"
            txtSelected.text = selectedClothesName
        }

        clothes3.setOnClickListener {
            selectedClothesIndex = 3
            selectedClothesName = "Green Hoodie"
            txtSelected.text = selectedClothesName
        }

        btnNext.setOnClickListener {
            if (selectedClothesIndex == 0) {
                Toast.makeText(this, "Please select clothes", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, AccessoriesActivity::class.java)
                intent.putExtra("characterIndex", characterIndex)
                intent.putExtra("characterName", characterName)
                intent.putExtra("clothesIndex", selectedClothesIndex)
                intent.putExtra("clothesName", selectedClothesName)
                startActivity(intent)
            }
        }
    }
}