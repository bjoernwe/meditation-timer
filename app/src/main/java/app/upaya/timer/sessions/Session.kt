package app.upaya.timer.sessions

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "sessions")
data class Session @JvmOverloads constructor(

    @PrimaryKey(autoGenerate = true)
    val sessionId: Long = 0L,

    @ColumnInfo(name = "end_time", index = true)
    val endTime: Date = Date(),

    @ColumnInfo
    val length: Int,

)
