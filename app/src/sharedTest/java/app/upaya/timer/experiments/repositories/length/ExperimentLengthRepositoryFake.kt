package app.upaya.timer.experiments.repositories.length


class ExperimentLengthRepositoryFake(
    private var experimentLength: Double = 10.0
) : IExperimentLengthRepository {

    override fun loadExperimentLength(): Double {
        return experimentLength
    }

    override fun storeExperimentLength(experimentLength: Double) {
        this.experimentLength = experimentLength
    }

}
