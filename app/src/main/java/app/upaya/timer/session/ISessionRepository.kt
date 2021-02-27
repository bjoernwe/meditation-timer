package app.upaya.timer.session


interface ISessionRepository {
    fun loadSessionLength(): Double
    fun storeSessionLength(sessionLength: Double)
}
