package app.upaya.timer.session.repository

import app.upaya.timer.experiments.repositories.logs.ExperimentLog
import app.upaya.timer.experiments.repositories.logs.IExperimentLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.*


class ExperimentLogRepositoryFake : IExperimentLogRepository {

    private val _sessions: MutableStateFlow<ArrayList<ExperimentLog>> = MutableStateFlow(ArrayList())

    override val experiments: Flow<List<ExperimentLog>> = _sessions

    override val lastExperiment: Flow<ExperimentLog> = _sessions.map { it.last() }

    override fun storeExperiment(experimentLog: ExperimentLog) {
        _sessions.value.add(experimentLog)
        _sessions.value = _sessions.value
    }

}
