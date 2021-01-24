package app.upaya.timer.sessions

import java.util.*


fun List<Session>.aggregate() : SessionAggregate {
    if (this.isEmpty())
        return SessionAggregate(
            session_count = 0,
            avg_length = 0f,
            total_length = 0,
            date = Date(0)
        )
    return SessionAggregate(
            session_count = this.size,
            avg_length = this.map { session -> session.length }.average().toFloat(),
            total_length = this.map { session -> session.length }.sum(),
            date = this[0].endDate
    )
}
