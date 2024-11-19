package com.example.p1_jetpack_compose

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.p1_jetpack_compose.ui.theme.*
import com.example.p1_jetpack_compose.ui.theme.P1_Jetpack_ComposeTheme

class BmiActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P1_Jetpack_ComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    BmiScreen(modifier = Modifier.padding(it), onNavigateToMain = {
                        startActivity(Intent(this, MainActivity::class.java))
                    }, onNavigateToSensor = {
                        startActivity(Intent(this, SensorActivity::class.java))
                    })
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun BmiScreen(modifier: Modifier = Modifier, onNavigateToMain: () -> Unit, onNavigateToSensor: () -> Unit) {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var bmiResult by remember { mutableStateOf(MainActivity.bmiResult) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Red200),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Liczba naciśnięć z poprzedniej aktywności: ${MainActivity.clickCount}")
        Text("Dane z czujnika: ${MainActivity.sensorData}")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Waga (kg)") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Wzrost (m)") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val weightValue = weight.toFloatOrNull()
            val heightValue = height.toFloatOrNull()
            if (weightValue != null && heightValue != null && heightValue > 0) {
                val bmi = weightValue / (heightValue * heightValue)
                bmiResult = String.format("Twoje BMI: %.2f", bmi)
                MainActivity.bmiResult = bmiResult
            } else {
                bmiResult = "Nieprawidłowe dane"
                MainActivity.bmiResult = bmiResult
            }
        }) {
            Text("Oblicz BMI")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(" $bmiResult")

        Spacer(modifier = Modifier.height(32.dp))

        // Przejście do aktywności głównej
        Button(onClick = onNavigateToMain) {
            Text("Powrót do Ekranu Głównego")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Przejście do aktywności z czujnikami
        Button(onClick = onNavigateToSensor) {
            Text("Przejdź do Danych Czujnika")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BmiScreenPreview() {
    P1_Jetpack_ComposeTheme {
        BmiScreen(onNavigateToMain = {}, onNavigateToSensor = {})
    }
}