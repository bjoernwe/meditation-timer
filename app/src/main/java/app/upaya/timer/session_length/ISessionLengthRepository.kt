package app.upaya.timer.session_length


interface ISessionLengthRepository {
    fun loadSessionLength(): Double
    fun storeSessionLength(sessionLength: Double)
}
