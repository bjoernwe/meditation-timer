package app.upaya.timer.settings

import app.upaya.timer.experiments.repositories.length.IExperimentLengthRepository


class ExperimentLengthRepositoryFake(
    private var sessionLength: Double = 10.0
) : IExperimentLengthRepository {

    override fun loadExperimentLength(): Double {
        return sessionLength
    }

    override fun storeExperimentLength(experimentLength: Double) {
        this.sessionLength = experimentLength
    }

}
