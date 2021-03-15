package app.upaya.timer.session

import app.upaya.timer.hints.Hint
import kotlinx.coroutines.flow.StateFlow


enum class SessionRating {
    UP, DOWN
}


interface ISessionHandler {

    val sessionLength: StateFlow<Double>
    val currentHint: StateFlow<Hint>

    fun onSessionIdling()

    fun onSessionStarted()

    fun onSessionFinished()

    fun onRatingSubmitted(rating: SessionRating)

}
