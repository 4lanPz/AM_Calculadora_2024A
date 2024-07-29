package com.alanpz.calculadorea

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var resultDisplay: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultDisplay = findViewById(R.id.result_display)

        val buttons = mapOf(
            R.id.button_0 to "0", R.id.button_1 to "1", R.id.button_2 to "2",
            R.id.button_3 to "3", R.id.button_4 to "4", R.id.button_5 to "5",
            R.id.button_6 to "6", R.id.button_7 to "7", R.id.button_8 to "8",
            R.id.button_9 to "9", R.id.button_add to "+", R.id.button_subtract to "-",
            R.id.button_multiply to "*", R.id.button_divide to "/", R.id.button_clear to "C",
            R.id.button_equals to "=", R.id.button_sin to "sin", R.id.button_cos to "cos",
            R.id.button_tan to "tan", R.id.button_backspace to "←"
        )

        for ((id, value) in buttons) {
            findViewById<Button>(id).setOnClickListener {
                onButtonClicked(value)
            }
        }
    }

    private fun onButtonClicked(value: String) {
        when (value) {
            "=" -> calculateResult()
            "C" -> resultDisplay.text.clear()
            "←" -> handleBackspace()
            "sin", "cos", "tan" -> applyTrigonometricFunction(value)
            else -> resultDisplay.append(value)
        }
    }

    private fun handleBackspace() {
        val currentText = resultDisplay.text
        if (currentText.isNotEmpty()) {
            resultDisplay.setText(currentText.subSequence(0, currentText.length - 1))
        }
    }

    private fun calculateResult() {
        try {
            val expression = resultDisplay.text.toString()
            val result = evaluateExpression(expression)
            resultDisplay.setText(result.toString())
        } catch (e: Exception) {
            resultDisplay.setText("Error")
        }
    }

    private fun applyTrigonometricFunction(function: String) {
        try {
            val input = resultDisplay.text.toString().toDouble()
            val result = when (function) {
                "sin" -> sin(Math.toRadians(input))
                "cos" -> cos(Math.toRadians(input))
                "tan" -> tan(Math.toRadians(input))
                else -> 0.0
            }
            resultDisplay.setText(result.toString())
        } catch (e: Exception) {
            resultDisplay.setText("Error")
        }
    }

    private fun evaluateExpression(expression: String): Double {
        val expr = ExpressionBuilder(expression).build()
        return expr.evaluate()
    }
}
