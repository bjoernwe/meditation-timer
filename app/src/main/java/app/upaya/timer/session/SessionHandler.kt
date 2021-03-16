package app.upaya.timer.session

import app.upaya.timer.hints.Hint
import app.upaya.timer.hints.HintRepository
import app.upaya.timer.session.repository.ISessionRepository
import app.upaya.timer.session.repository.SessionLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*


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

    private lateinit var sessionLog: SessionLog

    override fun onSessionIdling() {
        sessionLog = SessionLog(hint = currentHint.value.id.toString())
        sessionLog.store()
    }

    override fun onSessionStarted() {
        sessionLog.apply {
            this.startDate = Date()
            this.store()
        }
    }

    override fun onSessionFinished() {
        sessionLog.apply {
            this.endDate = Date()
            this.store()
        }
    }

    override fun onRatingSubmitted(rating: Double) {
        sessionLengthUpdater.updateFromRating(rating)
        sessionLog.apply {
            this.ratingDate = Date()
            this.rating = rating.toFloat()
            this.store()
        }
        updateFlows()
    }

    private fun updateFlows() {
        _sessionLength.value = sessionLengthUpdater.value
        _currentHint.value = hintRepository.getRandomHint()
    }

    private fun SessionLog.store() {
        sessionRepository.storeSession(this)
    }

}
