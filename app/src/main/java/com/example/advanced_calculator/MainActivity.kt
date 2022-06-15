package com.example.advanced_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    lateinit var textInput: TextView
    var lastNum: Boolean = false
    var stateError: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textInput = findViewById(R.id.textInput)
    }

    fun onNumber(view: View) {
        if (stateError) {
            textInput.text = (view as Button).text
            stateError = false
        } else {
            textInput.append((view as Button).text)
        }

        lastNum = true
    }

    fun onDecimal(view: View) {
        if (lastNum && !stateError && !lastDot) {
            textInput.append(".")
            lastNum = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        if (lastNum && !stateError) {
            textInput.append((view as Button).text)
            lastNum = false
            lastDot = false
        }
    }

    fun onClear(view: View) {
        this.textInput.text = ""
        lastNum = false
        stateError = false
        lastDot = false
    }

    fun onEqual(view: View) {
        if (lastNum && !stateError) {
            val txt = textInput.text.toString()
            val expr = ExpressionBuilder(txt).build()
            try {
                val result = expr.evaluate()
                textInput.text = result.toString()
                lastDot = true
            } catch (ex: ArithmeticException) {
                textInput.text = "Error"
                stateError = true
                lastNum = false
            }
        }
    }
}