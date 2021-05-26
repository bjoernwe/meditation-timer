package app.upaya.timer.experiments.repositories.logs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.math.abs
import kotlin.random.Random


@Entity(tableName = "experiments")
data class ExperimentLog @JvmOverloads constructor(

        @PrimaryKey
        val experimentId: Long = abs(Random.nextLong()),

        @ColumnInfo(name = "probe")
        var probeId: UUID,

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
