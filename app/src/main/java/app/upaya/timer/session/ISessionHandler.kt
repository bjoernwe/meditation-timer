package app.upaya.timer.session


enum class SessionRating {
    UP, DOWN
}


interface ISessionHandler {

    val sessionLength: Double

    //fun onSessionIdling()

    //fun onSessionStarted()

    fun onSessionFinished()

    fun onRatingSubmitted(rating: SessionRating)

}
