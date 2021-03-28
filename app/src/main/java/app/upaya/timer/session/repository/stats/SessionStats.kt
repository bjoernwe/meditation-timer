package app.upaya.timer.session.repository.stats

import java.util.*


data class SessionStats(
        val sessionCount: Int = 0,
        val avgLength: Double? = null,
        val totalLength: Int? = null,
        val date: Date? = null
)