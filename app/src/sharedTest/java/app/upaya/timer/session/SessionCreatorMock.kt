package app.upaya.timer.session

import app.upaya.timer.hints.Hint
import app.upaya.timer.session.creator.ISessionCreator
import app.upaya.timer.session.repository.SessionLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*


data class OnRatingCallArgs(
    val date: Date,
    val rating: Double,
    val currentSessionLength: Double,
)


class SessionCreatorMock(
    private val onRatingSubmittedCalls: MutableList<SessionLog>,
    initialSessionLength: Double
) : ISessionCreator {

    private val _sessionLength = MutableStateFlow(initialSessionLength)
    override val sessionLength: StateFlow<Double> = _sessionLength

    override val currentHint: StateFlow<Hint>
        get() = TODO("Not yet implemented")

    override fun onRatingSubmitted(sessionLog: SessionLog) {
        onRatingSubmittedCalls.add(sessionLog)
    }

}
