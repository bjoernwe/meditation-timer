package app.upaya.timer.session

import app.upaya.timer.hints.Hint
import app.upaya.timer.hints.HintRepository
import app.upaya.timer.session.repository.ISessionRepository
import app.upaya.timer.session.repository.SessionLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SessionHandler(
    private val sessionRepository: ISessionRepository,
    private val hintRepository: HintRepository,
    initialSessionLength: Double,
    ) : ISessionHandler {

    private val sessionLengthUpdater = SessionLength(initialSessionLength)
    private val _sessionLength: MutableStateFlow<Double> = MutableStateFlow(sessionLengthUpdater.value)
    override val sessionLength: StateFlow<Double> = _sessionLength

    private val _currentHint: MutableStateFlow<Hint> = MutableStateFlow(hintRepository.getRandomHint())
    override val currentHint: StateFlow<Hint> = _currentHint

    override fun onSessionFinished() {
        val sessionLog = SessionLog(length = sessionLength.value.toInt())
        sessionRepository.storeSession(sessionLog)
    }

    override fun onRatingSubmitted(rating: SessionRating) {
        when (rating) {
            SessionRating.UP -> { sessionLengthUpdater.decrease() }
            SessionRating.DOWN -> { sessionLengthUpdater.increase() }
        }
        _sessionLength.value = sessionLengthUpdater.value
        _currentHint.value = hintRepository.getRandomHint()
    }

}
