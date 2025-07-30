package com.example.simplecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentInput = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.displayTextView)

        val buttonIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnPercent, R.id.btnDot
        )

        buttonIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                val value = (it as Button).text.toString()
                currentInput += value
                display.text = currentInput
            }
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            currentInput = ""
            display.text = ""
        }

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput = currentInput.dropLast(1)
                display.text = currentInput
            }
        }

        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            try {
                val result = eval(currentInput)
                display.text = result.toString()
                currentInput = result.toString()
            } catch (e: Exception) {
                display.text = "Error"
            }
        }

        findViewById<Button>(R.id.btnLightTheme).setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        findViewById<Button>(R.id.btnDarkTheme).setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun eval(expression: String): Double {
        return object {
            var pos = -1
            var ch = 0

            fun nextChar() {
                ch = if (++pos < expression.length) expression[pos].code else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.code) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < expression.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    x = when {
                        eat('+'.code) -> x + parseTerm()
                        eat('-'.code) -> x - parseTerm()
                        else -> return x
                    }
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    x = when {
                        eat('*'.code) -> x * parseFactor()
                        eat('/'.code) -> x / parseFactor()
                        else -> return x
                    }
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.code)) return parseFactor() // unary plus
                if (eat('-'.code)) return -parseFactor() // unary minus

                var x: Double
                val startPos = pos
                if (eat('('.code)) {
                    x = parseExpression()
                    eat(')'.code)
                } else if (ch in '0'.code..'9'.code || ch == '.'.code) {
                    while (ch in '0'.code..'9'.code || ch == '.'.code) nextChar()
                    x = expression.substring(startPos, pos).toDouble()
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }

                return x
            }
        }.parse()
    }
}
