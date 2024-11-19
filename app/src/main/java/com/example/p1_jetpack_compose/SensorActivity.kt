package com.example.p1_jetpack_compose

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.p1_jetpack_compose.ui.theme.*
import androidx.compose.ui.unit.dp
import com.example.p1_jetpack_compose.ui.theme.P1_Jetpack_ComposeTheme

class SensorActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        setContent {
            P1_Jetpack_ComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    SensorScreen(modifier = Modifier.padding(it), onNavigateToMain = {
                        startActivity(Intent(this, MainActivity::class.java))
                    }, onNavigateToBmi = {
                        startActivity(Intent(this, BmiActivity::class.java))
                    })
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            MainActivity.sensorData = "Accelerometer: x = $x, y = $y, z = $z"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

@Composable
fun SensorScreen(modifier: Modifier = Modifier, onNavigateToMain: () -> Unit, onNavigateToBmi: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Blue200),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Liczba naciśnięć z poprzedniej aktywności: ${MainActivity.clickCount}")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Twoje BMI: ${MainActivity.bmiResult}")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Dane z czujnika: ${MainActivity.sensorData}")

        Spacer(modifier = Modifier.height(32.dp))

        // Przejście do aktywności głównej
        Button(onClick = onNavigateToMain) {
            Text("Powrót do Ekranu Głównego")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Przejście do aktywności BMI
        Button(onClick = onNavigateToBmi) {
            Text("Przejdź do Kalkulatora BMI")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SensorScreenPreview() {
    P1_Jetpack_ComposeTheme {
        SensorScreen(onNavigateToMain = {}, onNavigateToBmi = {})
    }
}