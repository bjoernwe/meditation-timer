package app.upaya.timer.experiments.repositories.logs.room

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

    @TypeConverter
    fun uuidToString(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(value: String?): UUID? {
        return UUID.fromString(value)
    }

}
