package com.misterioes.shopbel.domain

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Util {
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    fun toDate(millisSinceEpoch: Long?): Date?
    {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }

    fun formatDate(date: Date): String {
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return format.format(date)
    }
}