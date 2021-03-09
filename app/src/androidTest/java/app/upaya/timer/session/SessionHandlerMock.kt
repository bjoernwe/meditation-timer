package app.upaya.timer.session

import java.util.*
import kotlin.collections.ArrayList


data class OnRatingCallArgs(
    val date: Date,
    val rating: SessionRating,
    val currentSessionLength: Double,
)


class SessionHandlerMock(
    private val onSessionFinishedCalls: MutableList<Date>,
    private val onRatingSubmittedCalls: MutableList<OnRatingCallArgs>
) : ISessionHandler {

    override fun onSessionFinished(sessionLog: SessionLog) {
        onSessionFinishedCalls.add(Date())
    }

    override fun onRatingSubmitted(rating: SessionRating, currentSessionLength: Double): Double {
        onRatingSubmittedCalls.add(OnRatingCallArgs(
            date = Date(),
            rating = rating,
            currentSessionLength = currentSessionLength,
        ))
        return 0.0
    }

}
