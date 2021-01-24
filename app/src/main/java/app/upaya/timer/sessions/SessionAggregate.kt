package app.upaya.timer.sessions

import java.util.*


data class SessionAggregate(
        val session_count: Int,
        val avg_length: Float,
        val total_length: Int,
        val date: Date
        )
