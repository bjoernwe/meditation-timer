package app.upaya.timer.sessions

import java.util.*


data class SessionAggregateNullable(
        val session_count: Int?,
        val avg_length: Float?,
        val total_length: Int,
        val date: Date?
        )
{
        fun toSessionAggregate() : SessionAggregate {
                return SessionAggregate(
                        session_count = session_count ?: 0,
                        avg_length = avg_length ?: 0f,
                        total_length = total_length,
                        date = date ?: Date(0L)
                )
        }
}
