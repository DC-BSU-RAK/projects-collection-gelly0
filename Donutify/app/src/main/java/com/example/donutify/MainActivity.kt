package com.example.donutify

import android.animation.AnimatorInflater
import android.app.Dialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var firstSelection: Int? = null   // TOP
    private var secondSelection: Int? = null  // BOTTOM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val top = findViewById<ImageView>(R.id.topSelection)
        val bottom = findViewById<ImageView>(R.id.bottomSelection)

        val resultBtn = findViewById<Button>(R.id.btnResult)
        val clearBtn = findViewById<Button>(R.id.btnClear)

        // Instructions Button
        val instructionsBtn = findViewById<ImageButton>(R.id.btnInstruction)

        instructionsBtn.setOnClickListener {
            showInstructions()
        }

        // Get all ingredient buttons
        val btnVanilla = findViewById<ImageButton>(R.id.btnVanilla)
        val btnStrawberry = findViewById<ImageButton>(R.id.btnStrawberry)
        val btnChocolate = findViewById<ImageButton>(R.id.btnChocolate)
        val btnChocoSprinkles = findViewById<ImageButton>(R.id.btnChocoSprinkles)
        val btnRainbowSprinkles = findViewById<ImageButton>(R.id.btnRainbowSprinkles)

        // Apply up-down animation to all ingredient buttons
        val ingredientButtons = listOf(btnVanilla, btnStrawberry, btnChocolate, btnChocoSprinkles, btnRainbowSprinkles)

        ingredientButtons.forEach { button ->
            val animation = AnimatorInflater.loadAnimator(this, R.animator.up_down)
            animation.setTarget(button)
            animation.start()
        }

        // Helper: check sprinkles
        fun isSprinkle(imageRes: Int): Boolean {
            return imageRes == R.drawable.choco_sprinkles ||
                    imageRes == R.drawable.rainbow_sprinkles
        }

        fun selectIngredient(imageRes: Int) {

            // to avoid duplicate
            if (imageRes == firstSelection || imageRes == secondSelection) {
                Toast.makeText(this, "Already selected!", Toast.LENGTH_SHORT).show()
                return
            }

            // sprinkles rule: only ONE topping allowed
            val firstIsSprinkle = firstSelection?.let { isSprinkle(it) } ?: false
            val secondIsSprinkle = secondSelection?.let { isSprinkle(it) } ?: false
            val currentIsSprinkle = isSprinkle(imageRes)

            if (currentIsSprinkle && (firstIsSprinkle || secondIsSprinkle)) {
                Toast.makeText(this, "Only one sprinkle allowed!", Toast.LENGTH_SHORT).show()
                return
            }

            // TOP
            if (firstSelection == null) {
                firstSelection = imageRes
                top.setImageResource(imageRes)
                return
            }

            // BOTTOM
            if (secondSelection == null) {
                secondSelection = imageRes
                bottom.setImageResource(imageRes)
                return
            }

            Toast.makeText(this, "Only 2 ingredients allowed!", Toast.LENGTH_SHORT).show()
        }

        // Icing Flavours
        btnVanilla.setOnClickListener {
            selectIngredient(R.drawable.vanilla_icing)
        }

        btnStrawberry.setOnClickListener {
            selectIngredient(R.drawable.strawberry_icing)
        }

        btnChocolate.setOnClickListener {
            selectIngredient(R.drawable.chocolate_icing)
        }

        // Sprinkles Toppings
        btnChocoSprinkles.setOnClickListener {
            selectIngredient(R.drawable.choco_sprinkles)
        }

        btnRainbowSprinkles.setOnClickListener {
            selectIngredient(R.drawable.rainbow_sprinkles)
        }

        // Results
        resultBtn.setOnClickListener {
            if (firstSelection != null && secondSelection != null) {
                showPopup()
            } else {
                Toast.makeText(this, "Select 2 ingredients!", Toast.LENGTH_SHORT).show()
            }
        }

        // Clear
        clearBtn.setOnClickListener {
            firstSelection = null
            secondSelection = null

            top.setImageResource(android.R.color.transparent)
            bottom.setImageResource(android.R.color.transparent)

            Toast.makeText(this, "Cleared!", Toast.LENGTH_SHORT).show()
        }
    }

    // Instructions Popup Result
    private fun showInstructions() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.instructions)

        val closeBtn = dialog.findViewById<Button>(R.id.btnClose)

        closeBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    // Popup Results
    private fun showPopup() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_result)

        val popupImage = dialog.findViewById<ImageView>(R.id.popupImage)
        val popupText = dialog.findViewById<TextView>(R.id.popupText)
        val closeBtn = dialog.findViewById<Button>(R.id.btnClose)

        val first = firstSelection
        val second = secondSelection

        var message = "Your donut is ready!"
        var imageRes = R.drawable.vanilla_icing

        // Helper: check if ingredient is sprinkles
        fun isSprinkle(res: Int): Boolean {
            return res == R.drawable.choco_sprinkles || res == R.drawable.rainbow_sprinkles
        }

        // Helper: check if ingredient is icing
        fun isIcing(res: Int): Boolean {
            return res == R.drawable.vanilla_icing ||
                    res == R.drawable.strawberry_icing ||
                    res == R.drawable.chocolate_icing
        }

        // Normalize order for sprinkles+icing combinations
        val top = first!!
        val bottom = second!!
        val isSprinkleIcingCombo = (isSprinkle(top) && isIcing(bottom)) || (isIcing(top) && isSprinkle(bottom))

        val normalizedTop: Int
        val normalizedBottom: Int

        if (isSprinkleIcingCombo) {
            // For sprinkle+icing combos, always put icing on top for consistent lookup
            if (isIcing(top) && isSprinkle(bottom)) {
                normalizedTop = top
                normalizedBottom = bottom  // Already icing on top
            } else {
                normalizedTop = bottom
                normalizedBottom = top  // Swap so icing is on top
            }
        } else {
            normalizedTop = top
            normalizedBottom = bottom  // Keep original order for strict combinations
        }

        when {

            // ===== CHOCOLATE ICING CASES =====
            normalizedTop == R.drawable.chocolate_icing && normalizedBottom == R.drawable.rainbow_sprinkles -> {
                message = "Party Fudge"
                imageRes = R.drawable.choco_rainbow_donut
            }

            normalizedTop == R.drawable.chocolate_icing && normalizedBottom == R.drawable.choco_sprinkles -> {
                message = "Blackout Crunch"
                imageRes = R.drawable.double_choco_donut
            }

            normalizedTop == R.drawable.chocolate_icing && normalizedBottom == R.drawable.vanilla_icing -> {
                message = "Cookies & Cream Swirl"
                imageRes = R.drawable.choco_vanilla_swirl
            }

            normalizedTop == R.drawable.chocolate_icing && normalizedBottom == R.drawable.strawberry_icing -> {
                message = "Choco-Berry Ripple"
                imageRes = R.drawable.choco_strawberry_swirl
            }

            // ===== STRAWBERRY ICING CASES =====
            normalizedTop == R.drawable.strawberry_icing && normalizedBottom == R.drawable.rainbow_sprinkles -> {
                message = "Berry Confetti"
                imageRes = R.drawable.strawberry_rainbow_donut
            }

            normalizedTop == R.drawable.strawberry_icing && normalizedBottom == R.drawable.choco_sprinkles -> {
                message = "Strawberry Truffle Pop"
                imageRes = R.drawable.choco_strawberry_donut
            }

            normalizedTop == R.drawable.strawberry_icing && normalizedBottom == R.drawable.chocolate_icing -> {
                message = "Choco Strawberry Swirl"
                imageRes = R.drawable.strawberry_choco_swirl
            }

            normalizedTop == R.drawable.strawberry_icing && normalizedBottom == R.drawable.vanilla_icing -> {
                message = "Berry & Cream Dream"
                imageRes = R.drawable.strawberry_vanilla_swirl
            }

            // ===== VANILLA ICING CASES =====
            normalizedTop == R.drawable.vanilla_icing && normalizedBottom == R.drawable.rainbow_sprinkles -> {
                message = "Unicorn Burst"
                imageRes = R.drawable.vanilla_rainbow_donut
            }

            normalizedTop == R.drawable.vanilla_icing && normalizedBottom == R.drawable.choco_sprinkles -> {
                message = "Cookie Crunch"
                imageRes = R.drawable.choco_vanilla_donut
            }

            // STRICT ORDER FOR ICING+ICING COMBOS
            first == R.drawable.vanilla_icing && second == R.drawable.chocolate_icing -> {
                message = "Choco-Vanilla Zigzag"
                imageRes = R.drawable.vanilla_choco_swirl
            }

            first == R.drawable.chocolate_icing && second == R.drawable.vanilla_icing -> {
                message = "Vanilla-Choco Zigzag"
                imageRes = R.drawable.choco_vanilla_swirl
            }

            first == R.drawable.vanilla_icing && second == R.drawable.strawberry_icing -> {
                message = "Shortcake Swirl"
                imageRes = R.drawable.vanilla_strawberry_swirl
            }

            first == R.drawable.strawberry_icing && second == R.drawable.vanilla_icing -> {
                message = "Vanilla Strawberry Swirl"
                imageRes = R.drawable.strawberry_vanilla_swirl
            }
        }

        popupText.text = message
        popupImage.setImageResource(imageRes)

        closeBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}