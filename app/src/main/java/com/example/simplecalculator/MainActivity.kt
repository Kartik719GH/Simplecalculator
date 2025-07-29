package com.example.simplecalculator  // ✅ Ensure package matches

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // ✅ This must match your layout file name

        val input1 = findViewById<EditText>(R.id.input1)
        val input2 = findViewById<EditText>(R.id.input2)
        val addButton = findViewById<Button>(R.id.addButton)
        val resultText = findViewById<TextView>(R.id.resultText)

        addButton.setOnClickListener {
            val num1 = input1.text.toString().toIntOrNull()
            val num2 = input2.text.toString().toIntOrNull()

            if (num1 != null && num2 != null) {
                val sum = num1 + num2
                resultText.text = "Result: $sum"
            } else {
                resultText.text = "Please enter valid numbers"
            }
        }
    }
}