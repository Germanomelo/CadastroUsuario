package com.gmsp.desafiocadastro.util

import android.icu.text.SimpleDateFormat
import java.text.ParseException
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateManager {

    companion object {
        private val DATE_FORMAT = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        fun dateToString(date: Date): String = DATE_FORMAT.format(date)

        fun stringToDate(dateString: String): Date = DATE_FORMAT.parse(dateString)

        fun calculateUserAgetoString(birthDate: Date): String {
            return "${calculateUserAge(birthDate)} anos"
        }
        fun calculateUserAgetoString(birthDate: String): String {
            return "${calculateUserAge(birthDate)} anos"
        }

        fun calculateUserAge(birthDate: Date): Int {
            val today = Calendar.getInstance()
            val calBirthDate = Calendar.getInstance().apply { time = birthDate }

            var age = today.get(Calendar.YEAR) - calBirthDate.get(Calendar.YEAR)
            if (today.get(Calendar.MONTH) < calBirthDate.get(Calendar.MONTH) ||
                (today.get(Calendar.MONTH) == calBirthDate.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < calBirthDate.get(Calendar.DAY_OF_MONTH))
            ) {
                age--
            }
            return age
        }

        fun calculateUserAge(birthDate: String): Int = calculateUserAge(stringToDate(birthDate))

        fun isDateGreaterThanToday(date: Date): Boolean {
            val today = Calendar.getInstance()
            val calDate = Calendar.getInstance().apply { time = date }
            return calDate.after(today)
        }

        fun isDateGreaterThanToday(date: String): Boolean = isDateGreaterThanToday(stringToDate(date))

        fun isDateValid(date: String): Boolean {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateFormat.isLenient = false

            return try {
                val dataObj = dateFormat.parse(date)

                // Verificar se o dia e o mês são válidos
                val calendar = Calendar.getInstance()
                calendar.time = dataObj

                val dia = calendar.get(Calendar.DAY_OF_MONTH)
                val mes = calendar.get(Calendar.MONTH) + 1

                dia in 1..31 && mes in 1..12
            } catch (e: ParseException) {
                false
            }
        }

    }
}