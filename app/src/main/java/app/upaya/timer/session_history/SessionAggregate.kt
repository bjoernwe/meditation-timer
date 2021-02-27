package app.upaya.timer.session_history

import java.util.*


class SessionAggregate(sessionCount: Int?, avgLength: Float?, val totalLength: Int, date: Date?) {
        val sessionCount: Int = sessionCount ?: 0
        val avgLength: Float = avgLength ?: 0f
        val date: Date = date ?: Date(0)
}
