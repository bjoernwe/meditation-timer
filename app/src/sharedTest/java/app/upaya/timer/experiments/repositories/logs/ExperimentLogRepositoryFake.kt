package app.upaya.timer.experiments.repositories.logs

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.*


class ExperimentLogRepositoryFake : IExperimentLogRepository {

    private val _experiments: MutableStateFlow<ArrayList<ExperimentLog>> = MutableStateFlow(ArrayList())

    override val experiments: Flow<List<ExperimentLog>> = _experiments

    override val lastExperiment: Flow<ExperimentLog> = _experiments.map { it.last() }

    override fun storeExperiment(experimentLog: ExperimentLog) {
        _experiments.value.add(experimentLog)
        _experiments.value = _experiments.value
    }

}
