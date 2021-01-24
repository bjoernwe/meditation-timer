package app.upaya.timer.sessions

import java.util.*


fun List<Session>.aggregate() : SessionAggregate {
    if (this.isEmpty())
        return SessionAggregate(
            sessionCount = 0,
            avgLength = 0f,
            totalLength = 0,
            date = Date(0)
        )
    return SessionAggregate(
            sessionCount = this.size,
            avgLength = this.map { session -> session.length }.average().toFloat(),
            totalLength = this.map { session -> session.length }.sum(),
            date = Date(this.map { session -> session.endDate.time }.average().toLong())
    )
}
