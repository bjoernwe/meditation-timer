package app.upaya.timer.session.creator

import app.upaya.timer.hints.Hint
import app.upaya.timer.hints.HintRepository
import app.upaya.timer.session.repository.SessionLog
import app.upaya.timer.settings.SessionLengthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SessionCreator(
    private val hintRepository: HintRepository,
    private val sessionLengthRepository: SessionLengthRepository,
    ) : ISessionCreator {

    private val sessionLengthUpdater = SessionLength(sessionLengthRepository.loadSessionLength())
    private val _sessionLength: MutableStateFlow<Double> = MutableStateFlow(sessionLengthUpdater.value)
    override val sessionLength: StateFlow<Double> = _sessionLength

    private val _currentHint: MutableStateFlow<Hint> = MutableStateFlow(hintRepository.getRandomHint())
    override val currentHint: StateFlow<Hint> = _currentHint

    override fun onRatingSubmitted(sessionLog: SessionLog) {
        _currentHint.value = hintRepository.getRandomHint()
        sessionLog.rating?.let {
            val newSessionLength = sessionLengthUpdater.updateFromRating(it.toDouble())
            _sessionLength.value = newSessionLength
            sessionLengthRepository.storeSessionLength(newSessionLength)
        }
    }

}
