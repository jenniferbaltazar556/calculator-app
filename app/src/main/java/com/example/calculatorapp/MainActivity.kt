package com.example.calculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculatorapp.ui.theme.CalculatorAppTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

fun eval(expression: String): Double {
    return ExpressionBuilder(expression).build().evaluate()
}

@Composable
fun CalculatorLayout(modifier: Modifier = Modifier) {

    var input by remember {mutableStateOf("")}

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = input,
            fontSize = 32.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(12.dp)
        )

        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("0", ".", "=", "+"),
            listOf("Clear")
        )

        buttons.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                row.forEach { label ->
                    Button(
                        onClick = {
                            input = when (label) {
                                "=" -> try {
                                    eval(input).toString()
                                } catch (e: Exception) { "Error" }
                                "Clear" -> ""
                                else -> input + label
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(label)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorAppTheme {
        CalculatorLayout()
    }
}