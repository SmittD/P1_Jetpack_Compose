package com.example.p1_jetpack_compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.example.p1_jetpack_compose.ui.theme.P1_Jetpack_ComposeTheme
import com.example.p1_jetpack_compose.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    companion object {
        var clickCount = 0
        var bmiResult = ""
        var sensorData = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P1_Jetpack_ComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MainScreen(modifier = Modifier.padding(it), onNavigateToBmi = {
                        startActivity(Intent(this, BmiActivity::class.java))
                    }, onNavigateToSensor = {
                        startActivity(Intent(this, SensorActivity::class.java))
                    })
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, onNavigateToBmi: () -> Unit, onNavigateToSensor: () -> Unit) {
    var currentTime by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Teal200),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Czas wyświetlony po naciśnięciu przycisku
        Button(onClick = {
            currentTime = getCurrentTime()
            MainActivity.clickCount++
        }) {
            Text("Pokaż Obecny Czas")
        }
        if (currentTime.isNotEmpty()) {
            Text(currentTime)
        }
        Text("Liczba naciśnięć: ${MainActivity.clickCount}")
        Text("Twoje BMI: ${MainActivity.bmiResult}")
        Text("Dane z czujnika: ${MainActivity.sensorData}")

        Spacer(modifier = Modifier.height(32.dp))

        // Przejście do aktywności BMI
        Button(onClick = onNavigateToBmi) {
            Text("Przejdź do Kalkulatora BMI")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Przejście do aktywności z czujnikami
        Button(onClick = onNavigateToSensor) {
            Text("Przejdź do Danych Czujnika")
        }
    }
}

fun getCurrentTime(): String {
    val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Date())
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    P1_Jetpack_ComposeTheme {
        MainScreen(onNavigateToBmi = {}, onNavigateToSensor = {})
    }
}