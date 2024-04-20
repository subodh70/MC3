package com.ayush.a31

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.sensorapp.database.MyDataSource
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
//import java.nio.file.Path


class secact: ComponentActivity() {
    private lateinit var dataSource: MyDataSource
    private val sensorData: MutableState<List<SD>?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataSource = MyDataSource(this)

        lifecycleScope.launch {
            val data = dataSource.getAllData()
            Log.d("DestinationActivity", "Fetched data: $data")
            sensorData.value = data
        }

        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                if (sensorData.value == null) {
                    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                } else {
                    val data = sensorData.value!!

                    Column {
                        Text(text = "X vs Time")
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            SDGraphX(sensorData = data)

                        }
                        Text(text = "Y vs Time")
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            SDGraphY(sensorData = data)

                        }
                        Text(text = "Z vs Time")
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            SDGraphZ(sensorData = data)

                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SDGraphX(sensorData: List<SD>) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val graphColor = Color.Green // Change color as needed
            val padding = 50f // Adjust padding based on your layout
            val centerY = size.height / 2f

            val xMax = 10 // Consider only 10 values for X-axis
            val yMax = 10  // Consider max positive and negative values

            val dataStepX = (size.width - 2 * padding) / xMax
            val dataStepY = (centerY - padding) / yMax

            var step = 0

            // Draw X-axis in the middle
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(size.width - padding, centerY)
            )

            // Draw Y-axis lines (positive and negative)
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(padding, padding)
            )
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(padding, size.height - padding)
            )

            drawContext.canvas.nativeCanvas.drawText(
                "Time",
                size.width - padding - 50f, // Adjust position
                centerY + 30f, // Adjust position
                Paint().apply {
                    color = graphColor.toArgb()
                    textSize = 32f // Adjust text size
                }
            )


            val path = Path()
            val tenValues = sensorData.take(10) // Only consider first 10 values
            tenValues.forEachIndexed { index, data ->
                val x = padding + step * dataStepX
                val y =
                    if (data.y >= 0) centerY - data.x * dataStepY else centerY + Math.abs(data.y) * dataStepY
                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
                step += 1
            }
            drawPath(
                path,
                color = graphColor,
                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }

    @Composable
    fun SDGraphY(sensorData: List<SD>) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val graphColor = Color.Blue // Change color as needed
            val padding = 50f // Adjust padding based on your layout
            val centerY = size.height / 2f

            val xMax = 10 // Consider only 10 values for X-axis
            val yMax = 10  // Consider max positive and negative values

            val dataStepX = (size.width - 2 * padding) / xMax
            val dataStepY = (centerY - padding) / yMax

            var step = 0

            // Draw X-axis in the middle
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(size.width - padding, centerY)
            )

            // Draw Y-axis lines (positive and negative)
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(padding, padding)
            )
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(padding, size.height - padding)
            )

            drawContext.canvas.nativeCanvas.drawText(
                "Time",
                size.width - padding - 50f, // Adjust position
                centerY + 30f, // Adjust position
                Paint().apply {
                    color = graphColor.toArgb()
                    textSize = 32f // Adjust text size
                }
            )

            val path = Path()
            val tenValues = sensorData.take(10) // Only consider first 10 values
            tenValues.forEachIndexed { index, data ->
                val x = padding + step * dataStepX
                val y =
                    if (data.y >= 0) centerY - data.y * dataStepY else centerY + Math.abs(data.y) * dataStepY
                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
                step += 1
            }
            drawPath(
                path,
                color = graphColor,
                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }

    @Composable
    fun SDGraphZ(sensorData: List<SD>) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val graphColor = Color.Red // Change color as needed
            val padding = 50f // Adjust padding based on your layout
            val centerY = size.height / 2f

            val xMax = 10 // Consider only 10 values for X-axis
            val yMax = 10  // Consider max positive and negative values

            val dataStepX = (size.width - 2 * padding) / xMax
            val dataStepY = (centerY - padding) / yMax

            var step = 0

            // Draw X-axis in the middle
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(size.width - padding, centerY)
            )

            // Draw Y-axis lines (positive and negative)
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(padding, padding)
            )
            drawLine(
                color = graphColor,
                start = Offset(padding, centerY),
                end = Offset(padding, size.height - padding)
            )

            drawContext.canvas.nativeCanvas.drawText(
                "Time",
                size.width - padding - 50f, // Adjust position
                centerY + 30f, // Adjust position
                Paint().apply {
                    color = graphColor.toArgb()
                    textSize = 32f // Adjust text size
                }
            )

            val path = Path()
            val tenValues = sensorData.take(10) // Only consider first 10 values
            tenValues.forEachIndexed { index, data ->
                val x = padding + step * dataStepX
                val y =
                    if (data.y >= 0) centerY - data.z * dataStepY else centerY + Math.abs(data.y) * dataStepY
                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
                step += 1
            }
            drawPath(
                path,
                color = graphColor,
                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}