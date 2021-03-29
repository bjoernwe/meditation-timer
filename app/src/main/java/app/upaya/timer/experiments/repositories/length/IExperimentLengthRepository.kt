package app.upaya.timer.experiments.repositories.length


interface IExperimentLengthRepository {
    fun loadExperimentLength(): Double
    fun storeExperimentLength(experimentLength: Double)
}
