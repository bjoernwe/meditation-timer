package app.upaya.timer.session


class SessionHandler(initialSessionLength: Double = 10.0) {

    private lateinit var currentSession: SessionDetails
    var sessionLength: Double = initialSessionLength
        private set

    fun onSessionIdling() {
        //currentSession = SessionDetails(length = 0)
        //sessionRepository.storeSession(currentSession)
    }

    fun onSessionStarted() {
        //currentSession.startDate = Date()
        //sessionRepository.storeSession(currentSession)
    }

    fun onSessionFinished() {
        //currentSession.endDate = Date()
        //sessionRepository.storeSession(currentSession)
    }

    fun onRatingSubmitted(rating: SessionRating): Double {
        return when (rating) {
            SessionRating.UP -> { decreaseSessionLength() }
            SessionRating.DOWN -> { increaseSessionLength() }
        }
    }

    private fun decreaseSessionLength() : Double {
        val newSessionLength = sessionLength.times(0.8)
        if (newSessionLength >= 1.0) { sessionLength = newSessionLength }
        return newSessionLength
    }

    private fun increaseSessionLength() : Double {
        sessionLength = sessionLength.times(1.1)
        return sessionLength
    }

}
