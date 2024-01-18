package com.crepass.getpermissiontest.utils

import android.content.Context
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log

class CalendarData {

    val TAG: String = CalendarData::class.java.simpleName
    fun getCalendarData(context: Context): MutableList<CalendarEvent> {
        Log.d(TAG, "캘린더 로그 들어옴")
        val events = mutableListOf<CalendarEvent>()

        val projection = arrayOf(
            CalendarContract.Events._ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DESCRIPTION,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND
        )

        val cursor = context.contentResolver.query(
            CalendarContract.Events.CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val idCol = it.getColumnIndex(projection[0])
                val titleCol = it.getColumnIndex(projection[1])
                val descriptionCol = it.getColumnIndex(projection[2])
                val dtstartCol = it.getColumnIndex(projection[3])
                val dtendCol = it.getColumnIndex(projection[4])

                do {
                    val id = it.getLong(idCol)
                    val title = it.getString(titleCol)
                    val description = it.getString(descriptionCol)
                    val dtstart = it.getLong(dtstartCol)
                    val dtend = it.getLong(dtendCol)

                    events.add(CalendarEvent(id, title, description, dtstart, dtend))
                } while (it.moveToNext())
            }
        }
        Log.d("캘린더 정보 ", events.toString())
        return events
    }

    data class CalendarEvent(
        val id: Long,
        val title: String?,
        val description: String?,
        val dtStart: Long,
        val dtEnd: Long
    )

}