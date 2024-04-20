package com.ayush.a31

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ayush.a31.ui.theme.A31Theme
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.sensorapp.database.MyDataSource
import java.io.File

class MainActivity : ComponentActivity() {
    private lateinit var sensorManager: SensorManager
    private var accelerometerSensor: Sensor? = null
    private lateinit var dataSource: MyDataSource
    private var orientationState by mutableStateOf(floatArrayOf(0f, 0f, 0f))
    private var isActivityActive by mutableStateOf(false)

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataSource = MyDataSource(this)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val fileName = "stats.txt"

        setContent {
            val context = LocalContext.current

            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            val intent = Intent(context, secact::class.java)
                            context.startActivity(intent)
                        }
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "GRAPHS")
                    }
                }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    SensorDataDisplay(orientationState)
                    Button(onClick = { dataSource.exportDataToFile(fileName)}) {
                        Text(text = "Export Data")
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isActivityActive = true
        accelerometerSensor?.let {
            sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        isActivityActive = false
        sensorManager.unregisterListener(sensorListener)
    }

    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor == accelerometerSensor && isActivityActive) {
                Log.d("SensorListener", "Accelerometer data changed: ${event.values[0]}, ${event.values[1]}, ${event.values[2]}")
                orientationState = calculateOrientation(event.values)
                val currentTimeMillis = System.currentTimeMillis()
                dataSource.insertData(
                    currentTimeMillis,
                    orientationState[0].toString(),
                    orientationState[1].toString(),
                    orientationState[2].toString()
                )
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.d("SensorListener", "Accuracy changed: $accuracy")
        }
    }

    private fun calculateOrientation(accelerometerData: FloatArray): FloatArray {
        return accelerometerData
    }

    @Composable
    fun SensorDataDisplay(orientation: FloatArray) {
        Column {
            SensorDataRow("X", orientation[0])
            SensorDataRow("Y", orientation[1])
            SensorDataRow("Z", orientation[2])
        }
    }

    @Composable
    fun SensorDataRow(label: String, value: Float) {
        Surface(

        ) {
            Row(
                modifier = Modifier.padding(16.dp), // Adds padding around each row
                horizontalArrangement = Arrangement.SpaceBetween // Aligns the items evenly
            ) {
                Text(text = label, modifier = Modifier.width(40.dp)) // Sets a fixed width for the label
                Text(text = value.toString())
            }
        }
    }
}
