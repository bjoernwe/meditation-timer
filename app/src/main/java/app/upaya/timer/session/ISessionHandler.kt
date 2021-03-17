package app.upaya.timer.session

import app.upaya.timer.hints.Hint
import kotlinx.coroutines.flow.StateFlow


interface ISessionHandler {

    val sessionLength: StateFlow<Double>
    val currentHint: StateFlow<Hint>

    fun onSessionIdling()

    fun onSessionStarted()

    fun onSessionFinished()

    fun onRatingSubmitted(rating: Double)

}
