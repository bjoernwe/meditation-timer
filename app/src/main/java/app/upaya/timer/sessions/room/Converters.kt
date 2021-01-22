package app.upaya.timer.sessions.room

import androidx.room.TypeConverter
import java.util.*


class Converters {

    @TypeConverter
    fun fromTimestamp(value: Int?): Date? {
        return value?.let { Date(1000L * it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Int? {
        return date?.time?.div(1000)?.toInt()
    }

}
