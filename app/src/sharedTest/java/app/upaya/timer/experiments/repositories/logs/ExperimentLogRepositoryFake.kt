package app.upaya.timer.experiments.repositories.logs

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map


class ExperimentLogRepositoryFake : IExperimentLogRepository {

    private val _experiments: MutableStateFlow<MutableList<ExperimentLog>> = MutableStateFlow(mutableListOf())

    override val experiments: Flow<List<ExperimentLog>> = _experiments

    override val lastExperiment: Flow<ExperimentLog> = _experiments.map { it.first() }

    override fun storeExperiment(experimentLog: ExperimentLog) {
        _experiments.value.add(experimentLog)
        _experiments.value.sortByDescending { it.initDate }
        _experiments.value = _experiments.value
    }

}
