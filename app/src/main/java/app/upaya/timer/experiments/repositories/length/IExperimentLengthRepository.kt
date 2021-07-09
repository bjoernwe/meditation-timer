package app.upaya.timer.experiments.repositories.length


interface IExperimentLengthRepository {
    fun loadExperimentLength(key: String, default: Double = 10.0): Double
    fun storeExperimentLength(key: String, experimentLength: Double)
}
