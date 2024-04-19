package com.andresuryana.budgettrack.util.ext

import android.content.Context
import com.andresuryana.budgettrack.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateFormatterExt {

    private const val FORMAT_DATE_DEFAULT = "dd MMM yyyy"
    private const val FORMAT_DATE_WITH_DAY = "E, dd MMM yyyy"

    fun Date.formatDate(pattern: String = FORMAT_DATE_DEFAULT): String {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(this)
    }

    fun Date.formatRelativeDate(context: Context): String {
        val currentDate = Calendar.getInstance()
        currentDate.time = this

        return when {
            currentDate.isToday() -> context.getString(R.string.text_today)
            currentDate.isYesterday() -> context.getString(R.string.text_yesterday)
            else -> this.formatDate(FORMAT_DATE_WITH_DAY)
        }
    }

    private fun Calendar.isToday(): Boolean {
        val today = Calendar.getInstance()
        return this.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                this.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                this.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
    }

    private fun Calendar.isYesterday(): Boolean {
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_MONTH, -1)
        return this.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) &&
                this.get(Calendar.MONTH) == yesterday.get(Calendar.MONTH) &&
                this.get(Calendar.DAY_OF_MONTH) == yesterday.get(Calendar.DAY_OF_MONTH)
    }
}