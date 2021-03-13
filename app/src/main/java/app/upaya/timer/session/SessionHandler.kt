package app.upaya.timer.session

import app.upaya.timer.session.repository.ISessionRepository
import app.upaya.timer.session.repository.SessionLog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SessionHandler(
    private val sessionRepository: ISessionRepository,
    initialSessionLength: Double,
    ) : ISessionHandler {

    private val _sessionLength = SessionLength(initialSessionLength)
    override val sessionLength: Double
        get() = _sessionLength.value

    //override fun onSessionIdling() {
        //currentSession = SessionDetails(length = 0)
        //sessionRepository.storeSession(currentSession)
    //}

    //override fun onSessionStarted() {
        //currentSession.startDate = Date()
        //sessionRepository.storeSession(currentSession)
    //}

    override fun onSessionFinished() {
        val sessionLog = SessionLog(length = sessionLength.toInt())
        GlobalScope.launch {
            sessionRepository.storeSession(sessionLog)
        }
    }

    override fun onRatingSubmitted(rating: SessionRating) {
        return when (rating) {
            SessionRating.UP -> { _sessionLength.decrease() }
            SessionRating.DOWN -> { _sessionLength.increase() }
        }
    }

}
