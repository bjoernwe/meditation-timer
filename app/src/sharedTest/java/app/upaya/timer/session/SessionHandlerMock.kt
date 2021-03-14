package app.upaya.timer.session

import java.util.*


data class OnRatingCallArgs(
    val date: Date,
    val rating: SessionRating,
    val currentSessionLength: Double,
)


class SessionHandlerMock(
    private val onSessionFinishedCalls: MutableList<Date>,
    private val onRatingSubmittedCalls: MutableList<OnRatingCallArgs>,
    initialSessionLength: Double
) : ISessionHandler {

    override var sessionLength: Double = initialSessionLength
        private set

    override fun onSessionFinished() {
        onSessionFinishedCalls.add(Date())
    }

    override fun onRatingSubmitted(rating: SessionRating) {
        onRatingSubmittedCalls.add(OnRatingCallArgs(
            date = Date(),
            rating = rating,
            currentSessionLength = sessionLength,
        ))
    }

}
