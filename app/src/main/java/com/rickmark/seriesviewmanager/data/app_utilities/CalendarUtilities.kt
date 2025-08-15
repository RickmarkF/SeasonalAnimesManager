package com.rickmark.seriesviewmanager.data.app_utilities

import java.util.Calendar

class CalendarUtilities {
    companion object {

        private fun getCalendar(): Calendar {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            return calendar
        }

        fun determineDay(): Int {
            return getCalendar().get(Calendar.YEAR)
        }

        fun determineSeason(): String {
            val month: Int = getCalendar().get(Calendar.MONTH)
            var season: String = ""
            when (month) {
                Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH -> {
                    season = "winter"
                }

                Calendar.APRIL, Calendar.MAY, Calendar.JUNE -> {
                    season = "spring"
                }

                Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER -> {
                    season = "summer"
                }

                Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER -> {
                    season = "fall"
                }
            }
            return season
        }
    }
}