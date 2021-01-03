package app.upaya.timer.sessions

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "sessions")
data class Session @JvmOverloads constructor(

    @PrimaryKey(autoGenerate = true)
    val sessionId: Long = 0L,

    @ColumnInfo(name = "end_time")
    val endTime: Float = System.currentTimeMillis() / 1000f,  // Unix epoch timestamp

    @ColumnInfo
    val length: Int,

)
