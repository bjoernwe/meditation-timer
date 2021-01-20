package app.upaya.timer.timer


interface ITimerRepository {
    fun loadSessionLength(): Double
    fun storeSessionLength(sessionLength: Double)
}
