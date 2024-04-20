#Sensor App
This Android application, coded in Kotlin, is a simple sensor data monitoring tool. It utilizes the device's accelerometer sensor to capture orientation data along the X, Y, and Z axes, and displays it in real-time. Additionally, it provides functionality to export this data to a text file.

Features
Real-time display of accelerometer sensor data.
Export sensor data to a text file.
Navigation to another activity for viewing graphs (skeleton code provided).
Installation
Clone the repository to your local machine.
Open the project in Android Studio.
Build and run the project on an Android device or emulator.
Usage
Upon launching the app, you'll see the current accelerometer sensor data displayed for the X, Y, and Z axes. Pressing the "Export Data" button will save this data to a text file named stats.txt. Additionally, there's a floating action button that is intended to navigate to another activity (secact) for viewing graphs.

Dependencies
This project depends on the following libraries:

androidx.activity:activity-compose
androidx.compose.foundation:foundation-layout
androidx.compose.material3:material3
androidx.compose.runtime:runtime
androidx.compose.ui:ui
com.example.sensorapp:database (dependency details not provided)

Sensor Graph Activity
This Android activity, implemented in Kotlin, is designed to visualize sensor data using line graphs. It fetches sensor data from a database and displays three separate graphs for X, Y, and Z axes.

Features
Fetches sensor data from a database.
Displays line graphs for X, Y, and Z axes.
Provides visual representation of sensor data over time.
Usage
To integrate this activity into your Android application:

Copy the secact.kt file into your project's com.ayush.a31 package.
Ensure that you have a database setup similar to MyDataSource to provide sensor data.
Launch the secact activity from your main activity or navigation flow.
Dependencies
This activity requires the following dependencies:

androidx.activity:activity-compose
androidx.compose.foundation:foundation-layout
androidx.compose.material3:material3
androidx.compose.runtime:runtime
androidx.compose.ui:ui
Ensure that these dependencies are included in your project's build.gradle file.

Customization
You can customize the appearance and behavior of the graphs by adjusting the following parameters within the SDGraphX, SDGraphY, and SDGraphZ functions:

graphColor: Adjust the color of the graph lines.
padding: Modify the padding around the graph area.
xMax and yMax: Define the maximum values for the X and Y axes.
textSize: Adjust the size of text labels on the graph.
Feel free to experiment with these parameters to achieve the desired visual representation of your sensor data.
