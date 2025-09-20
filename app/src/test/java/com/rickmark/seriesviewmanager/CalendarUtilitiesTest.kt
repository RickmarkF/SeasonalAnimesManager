package com.rickmark.seriesviewmanager

import com.rickmark.seriesviewmanager.data.app_utilities.CalendarUtilities
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito.mockStatic
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.Calendar

class CalendarUtilitiesTest {

    @Mock
    private lateinit var calendar: Calendar

    private lateinit var mockedCalendarStatic: MockedStatic<Calendar>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mockedCalendarStatic = mockStatic(Calendar::class.java)
        mockedCalendarStatic.`when`<Calendar> { Calendar.getInstance() }.thenReturn(calendar)
    }

    @After
    fun tearDown() {
        mockedCalendarStatic.close()
    }

    @Test
    fun `determineDay returns the current year`() {
        `when`(calendar.get(Calendar.YEAR)).thenReturn(2023)

        val year: Int = CalendarUtilities.determineDay()
        assertEquals(2023, year)
    }

    @Test
    fun `determineSeason returns the correct season`() {
        `when`(calendar.get(Calendar.MONTH)).thenReturn(10)

        val season: String = CalendarUtilities.determineSeason()

        assertEquals("fall", season)
    }
}
