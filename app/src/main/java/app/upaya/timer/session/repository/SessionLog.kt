package app.upaya.timer.session.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.math.abs
import kotlin.random.Random


@Entity(tableName = "sessions")
data class SessionLog @JvmOverloads constructor(

        @PrimaryKey
        val sessionId: Long = abs(Random.nextLong()),

        @ColumnInfo(name = "hint")
        var hint: String,

        @ColumnInfo(name = "init_time", index = true)
        val initDate: Date = Date(),

        @ColumnInfo(name = "start_time")
        var startDate: Date? = null,

        @ColumnInfo(name = "end_time", index = true)
        var endDate: Date? = null,

        @ColumnInfo(name = "rating_time")
        var ratingDate: Date? = null,

        @ColumnInfo(name = "rating")
        var rating: Float? = null,

        )
