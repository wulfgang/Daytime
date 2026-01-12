package com.example.timebasediconapp

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timebasediconapp.ui.theme.TimeBasedIconAppTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set the appropriate icon based on current time
        setAppIconBasedOnTime()
        
        setContent {
            TimeBasedIconAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Update icon when app resumes (in case time changed)
        setAppIconBasedOnTime()
    }

    private fun setAppIconBasedOnTime() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        
        val packageManager = packageManager
        val componentNameToEnable: String
        val componentNamesToDisable = listOf(
            "com.example.timebasediconapp.MorningActivity",
            "com.example.timebasediconapp.NoonActivity", 
            "com.example.timebasediconapp.AfternoonActivity",
            "com.example.timebasediconapp.NightActivity"
        )

        when (hour) {
            in 5..11 -> {
                // Morning (5:00 - 11:59)
                componentNameToEnable = "com.example.timebasediconapp.MorningActivity"
            }
            in 12..16 -> {
                // Noon (12:00 - 16:59)
                componentNameToEnable = "com.example.timebasediconapp.NoonActivity"
            }
            in 17..20 -> {
                // Afternoon (17:00 - 20:59)
                componentNameToEnable = "com.example.timebasediconapp.AfternoonActivity"
            }
            else -> {
                // Night (21:00 - 4:59)
                componentNameToEnable = "com.example.timebasediconapp.NightActivity"
            }
        }

        try {
            // Disable all activity aliases first
            componentNamesToDisable.forEach { componentName ->
                packageManager.setComponentEnabledSetting(
                    android.content.ComponentName(this, componentName),
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP
                )
            }

            // Enable the appropriate activity alias
            packageManager.setComponentEnabledSetting(
                android.content.ComponentName(this, componentNameToEnable),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@Composable
fun MainContent() {
    val currentTime by produceState(initialValue = "") {
        while (true) {
            value = getCurrentTimeString()
            kotlinx.coroutines.delay(1000)
        }
    }

    val timeOfDay = getTimeOfDay()
    val timeInfo = getTimeInfo(timeOfDay)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Time display
        Text(
            text = currentTime,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Time of day
        Text(
            text = timeInfo.displayName,
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Icon description
        Text(
            text = timeInfo.iconDescription,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Info card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "App Icon Changes Based on Time",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "• Morning (5:00-11:59): Rising Sun\n" +
                          "• Noon (12:00-16:59): Full Sun\n" +
                          "• Afternoon (17:00-20:59): Setting Sun\n" +
                          "• Night (21:00-4:59): Moon",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    lineHeight = 20.sp
                )
            }
        }
    }
}

private fun getCurrentTimeString(): String {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val second = calendar.get(Calendar.SECOND)
    
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

private fun getTimeOfDay(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..11 -> "morning"
        in 12..16 -> "noon"
        in 17..20 -> "afternoon"
        else -> "night"
    }
}

private fun getTimeInfo(timeOfDay: String): TimeInfo {
    return when (timeOfDay) {
        "morning" -> TimeInfo(
            displayName = "Good Morning! ☀️",
            iconDescription = "Rising Sun icon is active"
        )
        "noon" -> TimeInfo(
            displayName = "Good Noon! 🌞",
            iconDescription = "Full Sun icon is active"
        )
        "afternoon" -> TimeInfo(
            displayName = "Good Afternoon! 🌅",
            iconDescription = "Setting Sun icon is active"
        )
        else -> TimeInfo(
            displayName = "Good Night! 🌙",
            iconDescription = "Moon icon is active"
        )
    }
}

data class TimeInfo(
    val displayName: String,
    val iconDescription: String
)
