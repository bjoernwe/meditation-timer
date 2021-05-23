package app.upaya.timer.experiments.repositories.length


class ExperimentLengthRepositoryFake(
    private val experimentLengths: MutableMap<String, Double> = mutableMapOf()
) : IExperimentLengthRepository {

    override fun loadExperimentLength(key: String, default: Double): Double {
        return experimentLengths.getOrDefault(key = key, defaultValue = default)
    }

    override fun storeExperimentLength(key: String, experimentLength: Double) {
        this.experimentLengths[key] = experimentLength
    }

}
