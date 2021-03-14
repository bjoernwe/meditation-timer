package app.upaya.timer.session.repository

import app.upaya.timer.session.repository.room.SessionLogDao
import kotlinx.coroutines.*


class SessionRepository(
    private val sessionLogDao: SessionLogDao,
    private val externalScope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : ISessionRepository {

    override val lastSession = sessionLogDao.getLastSession()
    override val sessions = sessionLogDao.getSessions()

    override fun storeSession(sessionLog: SessionLog) {
        externalScope.launch {
            withContext(ioDispatcher) {
                // Move session logging to another scope (not ViewModel) to make sure it's completed
                // independently of the UI lifecycle
                sessionLogDao.insert(sessionLog = sessionLog)
            }
        }
    }

}
