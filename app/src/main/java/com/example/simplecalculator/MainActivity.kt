package com.example.simplecalculator

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {

    private lateinit var display: EditText
    private var expression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        // Number buttons
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                val digit = (it as Button).text.toString()
                appendToExpression(digit)
            }
        }

        // Operator buttons
        findViewById<Button>(R.id.btnAdd).setOnClickListener { appendToExpression("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { appendToExpression("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { appendToExpression("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { appendToExpression("/") }
        findViewById<Button>(R.id.btnPercent).setOnClickListener { appendToExpression("%") }
        findViewById<Button>(R.id.btnDot).setOnClickListener { appendToExpression(".") }

        // Special buttons
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            expression = ""
            display.setText("")
        }

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            if (expression.isNotEmpty()) {
                expression = expression.dropLast(1)
                display.setText(expression)
            }
        }

        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            try {
                val expr = ExpressionBuilder(expression).build()
                val result = expr.evaluate()
                display.setText(result.toString())
                expression = result.toString()
            } catch (e: Exception) {
                display.setText("Error")
                expression = ""
            }
        }
    }

    private fun appendToExpression(value: String) {
        expression += value
        display.setText(expression)
    }
}