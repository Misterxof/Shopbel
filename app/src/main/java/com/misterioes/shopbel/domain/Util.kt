package com.misterioes.shopbel.domain

import java.util.Date

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
}