package app.upaya.timer.session_history.room_entries

import androidx.room.TypeConverter
import java.util.*


class Converters {

    @TypeConverter
    fun dateToTimestamp(date: Date?): Int? {
        // In SQL the date is stored in seconds since epoch not in milliseconds (due to the limited
        // value range and for grouping dates by 'strftime').
        return date?.time?.div(1000)?.toInt()
    }

    @TypeConverter
    fun fromTimestamp(value: Int?): Date? {
        return value?.let { Date(1000L * it) }
    }

}
