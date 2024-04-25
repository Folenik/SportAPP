package com.mosz.sportapp.extensionTests

import com.mosz.sportapp.utils.sortByDate
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale

class SortByDateTest {
    @Test
    fun sortByDate_SortsCorrectly() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        val dateStrings = listOf("2024-04-25T10:30:00.000", "2024-04-24T09:30:00.000", "2024-04-26T12:00:00.000")
        val dates = dateStrings.map { dateFormat.parse(it) }
        val unsortedList = listOf(
            TestItem("Item 1", dateStrings[0]),
            TestItem("Item 2", dateStrings[1]),
            TestItem("Item 3", dateStrings[2])
        )

        val sortedList = unsortedList.sortByDate { it.date }

        assertEquals(dates[1], dateFormat.parse(sortedList[0].date)) // Item 2 should come first
        assertEquals(dates[0], dateFormat.parse(sortedList[1].date)) // Item 1 should come second
        assertEquals(dates[2], dateFormat.parse(sortedList[2].date)) // Item 3 should come third
    }

    data class TestItem(val name: String, val date: String)
}