package de.fhe.adoptapal.data

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

class Converters {

    @TypeConverter
    fun stringToLocalDateTime(dateTimeString: String?): LocalDateTime {
        return if (dateTimeString == null) {
            LocalDateTime.MIN
        } else {
            LocalDateTime.parse(dateTimeString)
        }
    }

    @TypeConverter
    fun localDateTimeToString(dateTime: LocalDateTime?): String {
        return dateTime.toString()
    }


    @TypeConverter
    fun stringToLocalDate(dateString: String?): LocalDate {
        return if (dateString == null) {
            LocalDate.MIN
        } else {
            LocalDate.parse(dateString)
        }
    }

    @TypeConverter
    fun localDateToString(date: LocalDate?): String {
        return date.toString()
    }
}
