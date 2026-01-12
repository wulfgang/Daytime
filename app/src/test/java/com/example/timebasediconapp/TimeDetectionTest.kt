package com.example.timebasediconapp

import org.junit.Test
import org.junit.Assert.*
import java.util.*

class TimeDetectionTest {

    @Test
    fun testMorningTimeDetection() {
        // Test morning hours (5:00 - 11:59)
        val morningHours = listOf(5, 8, 11)
        morningHours.forEach { hour ->
            val timeOfDay = getTimeOfDayForHour(hour)
            assertEquals("morning", timeOfDay)
        }
    }

    @Test
    fun testNoonTimeDetection() {
        // Test noon hours (12:00 - 16:59)
        val noonHours = listOf(12, 14, 16)
        noonHours.forEach { hour ->
            val timeOfDay = getTimeOfDayForHour(hour)
            assertEquals("noon", timeOfDay)
        }
    }

    @Test
    fun testAfternoonTimeDetection() {
        // Test afternoon hours (17:00 - 20:59)
        val afternoonHours = listOf(17, 18, 20)
        afternoonHours.forEach { hour ->
            val timeOfDay = getTimeOfDayForHour(hour)
            assertEquals("afternoon", timeOfDay)
        }
    }

    @Test
    fun testNightTimeDetection() {
        // Test night hours (21:00 - 4:59)
        val nightHours = listOf(21, 23, 0, 2, 4)
        nightHours.forEach { hour ->
            val timeOfDay = getTimeOfDayForHour(hour)
            assertEquals("night", timeOfDay)
        }
    }

    @Test
    fun testAllHoursCovered() {
        // Test that all 24 hours are covered
        for (hour in 0..23) {
            val timeOfDay = getTimeOfDayForHour(hour)
            assertTrue("Hour $hour not covered", 
                timeOfDay in listOf("morning", "noon", "afternoon", "night"))
        }
    }

    private fun getTimeOfDayForHour(hour: Int): String {
        return when (hour) {
            in 5..11 -> "morning"
            in 12..16 -> "noon"
            in 17..20 -> "afternoon"
            else -> "night"
        }
    }
}
