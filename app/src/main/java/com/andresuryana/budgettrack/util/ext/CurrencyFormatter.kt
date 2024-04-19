package com.andresuryana.budgettrack.util.ext

import java.text.NumberFormat
import java.util.Locale

object CurrencyFormatter {

    // TODO: Move this later into app configuration!
    // Note that, changing currency means the amount also need to be updated!
    // Need to do some research about this..
    private val DEFAULT_LOCALE: Locale = Locale("in", "ID")

    fun Long?.formatCurrency(locale: Locale = DEFAULT_LOCALE): String {
        // Initialize the formatter
        val formatter = NumberFormat.getCurrencyInstance(locale)
        val symbol = formatter.currency?.getSymbol(locale)

        // Adding spacing between currency symbol and the amount
        return formatter.format(this).replace(symbol.toString(), " $symbol ").trim()
    }

    fun Long?.formatCurrencyHidden(locale: Locale = DEFAULT_LOCALE): String {
        val regex = "\\d".toRegex()
        return this.formatCurrency(locale).replace(regex) { matchResult ->
            "\u2022".repeat(matchResult.value.length)
        }
    }
}