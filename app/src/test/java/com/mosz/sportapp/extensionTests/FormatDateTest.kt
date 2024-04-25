package com.mosz.sportapp.extensionTests

import com.mosz.sportapp.utils.formatDate
import junit.framework.TestCase.assertEquals
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.test.Test

class FormatDateTest {

    @Test
    fun formatDate_ReturnsToday() {
        val today = Calendar.getInstance()
        val dateString = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(today.time)

        val formattedDate = dateString.formatDate()

        assertEquals("Today, ${today.get(Calendar.HOUR_OF_DAY)}:${today.get(Calendar.MINUTE)}", formattedDate)
    }

    @Test
    fun formatDate_ReturnsYesterday() {
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DATE, -1)
        val dateString = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(yesterday.time)

        val formattedDate = dateString.formatDate()

        assertEquals("Yesterday, ${yesterday.get(Calendar.HOUR_OF_DAY)}:${yesterday.get(Calendar.MINUTE)}", formattedDate)
    }

    @Test
    fun formatDate_ReturnsTomorrow() {
        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DATE, 1)
        val dateString = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(tomorrow.time)

        val formattedDate = dateString.formatDate()

        assertEquals("Tomorrow, ${tomorrow.get(Calendar.HOUR_OF_DAY)}:${tomorrow.get(Calendar.MINUTE)}", formattedDate)
    }

    @Test
    fun formatDate_ReturnsFormattedDate() {
        val customDate = Calendar.getInstance()
        customDate.set(2024, Calendar.APRIL, 23, 14, 30) // Custom date: 23rd April 2024, 14:30
        val dateString = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).format(customDate.time)

        val formattedDate = dateString.formatDate()

        assertEquals("23-04-2024", formattedDate)
    }
}