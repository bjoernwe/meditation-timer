package app.upaya.timer.experiments.repositories.logs

import app.upaya.timer.session.repository.room.SessionLogDao
import kotlinx.coroutines.*


class ExperimentLogRepository(
    private val sessionLogDao: SessionLogDao,
    private val externalScope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : IExperimentLogRepository {

    override val lastExperiment = sessionLogDao.getLastSession()
    override val experiments = sessionLogDao.getSessions()

    override fun storeExperiment(experimentLog: ExperimentLog) {
        externalScope.launch {
            withContext(ioDispatcher) {
                // Move session logging to another scope (not ViewModel) to make sure it's completed
                // independently of the UI lifecycle
                sessionLogDao.insert(experimentLog = experimentLog)
            }
        }
    }

}
