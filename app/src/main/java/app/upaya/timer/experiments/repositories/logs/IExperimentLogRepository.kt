package app.upaya.timer.experiments.repositories.logs

import kotlinx.coroutines.flow.Flow


interface IExperimentLogRepository {
    val lastExperiment: Flow<ExperimentLog>
    val experiments: Flow<List<ExperimentLog>>
    fun storeExperiment(experimentLog: ExperimentLog)
}
