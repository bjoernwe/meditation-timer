package app.upaya.timer.experiments.repositories.length


interface ISessionLengthRepository {
    fun loadSessionLength(): Double
    fun storeSessionLength(sessionLength: Double)
}
