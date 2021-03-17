package app.upaya.timer.session.creator

import app.upaya.timer.hints.Hint
import app.upaya.timer.session.repository.SessionLog
import kotlinx.coroutines.flow.StateFlow


interface ISessionCreator {
    val sessionLength: StateFlow<Double>
    val currentHint: StateFlow<Hint>
    fun onRatingSubmitted(sessionLog: SessionLog)
}
