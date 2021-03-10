package app.upaya.timer.settings


interface ISessionLengthRepository {
    fun loadSessionLength(): Double
    fun storeSessionLength(sessionLength: Double)
}
