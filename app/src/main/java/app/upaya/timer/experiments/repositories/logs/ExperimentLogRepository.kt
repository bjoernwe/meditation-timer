package app.upaya.timer.experiments.repositories.logs

import app.upaya.timer.experiments.repositories.logs.room.ExperimentLogDao
import kotlinx.coroutines.*


class ExperimentLogRepository(
    private val experimentLogDao: ExperimentLogDao,
    private val externalScope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : IExperimentLogRepository {

    override val lastExperiment = experimentLogDao.getLastExperiment()
    override val experiments = experimentLogDao.getExperiments()

    override fun storeExperiment(experimentLog: ExperimentLog) {
        externalScope.launch {
            withContext(ioDispatcher) {
                // Move experiment logging to another scope (not ViewModel) to make sure it's
                // completed independently of the UI lifecycle
                experimentLogDao.insert(experimentLog = experimentLog)
            }
        }
    }

}
