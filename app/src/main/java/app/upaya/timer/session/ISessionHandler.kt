package app.upaya.timer.session


enum class SessionRating {
    UP, DOWN
}


interface ISessionHandler {

    //fun onSessionIdling()

    //fun onSessionStarted()

    fun onSessionFinished(sessionLog: SessionLog)

    fun onRatingSubmitted(rating: SessionRating, currentSessionLength: Double): Double

}
