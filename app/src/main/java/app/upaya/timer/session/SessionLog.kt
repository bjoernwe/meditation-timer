package app.upaya.timer.session

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "sessions")
data class SessionLog @JvmOverloads constructor(

        @PrimaryKey(autoGenerate = true)
        val sessionId: Long = 0L,

        @ColumnInfo(name = "end_time", index = true)
        val endDate: Date = Date(),

        @ColumnInfo
        val length: Int,

    )
