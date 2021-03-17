package app.upaya.timer.session

import app.upaya.timer.session.creator.ISessionCreator
import java.util.*


data class OnRatingCallArgs(
    val date: Date,
    val rating: SessionRating,
    val currentSessionLength: Double,
)


class SessionCreatorMock(
    private val onSessionFinishedCalls: MutableList<Date>,
    private val onRatingSubmittedCalls: MutableList<OnRatingCallArgs>,
    initialSessionLength: Double
) : ISessionCreator {

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
