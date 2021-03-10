package app.upaya.timer.session

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SessionHandler(private val sessionLogRepository: ISessionLogRepository) : ISessionHandler {

    //override fun onSessionIdling() {
        //currentSession = SessionDetails(length = 0)
        //sessionRepository.storeSession(currentSession)
    //}

    //override fun onSessionStarted() {
        //currentSession.startDate = Date()
        //sessionRepository.storeSession(currentSession)
    //}

    override fun onSessionFinished(sessionLog: SessionLog) {
        GlobalScope.launch {
            sessionLogRepository.storeSession(sessionLog)
        }
    }

    override fun onRatingSubmitted(rating: SessionRating, currentSessionLength: Double): Double {
        return when (rating) {
            SessionRating.UP -> { decreaseSessionLength(currentSessionLength) }
            SessionRating.DOWN -> { increaseSessionLength(currentSessionLength) }
        }
    }

    private fun decreaseSessionLength(currentSessionLength: Double) : Double {
        val newSessionLength = currentSessionLength.times(0.8)
        return if (newSessionLength >= 1.0) {
            newSessionLength
        } else {
            currentSessionLength
        }
    }

    private fun increaseSessionLength(currentSessionLength: Double): Double {
        return currentSessionLength.times(1.1)
    }

}
