package app.upaya.timer.session.repository

import app.upaya.timer.session.repository.room.SessionLogDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SessionRepository(private val sessionLogDao: SessionLogDao) : ISessionRepository {

    override val lastSession = sessionLogDao.getLastSession()
    override val sessions = sessionLogDao.getSessions()

    override suspend fun storeSession(sessionLog: SessionLog) {
        withContext(Dispatchers.IO) {
            sessionLogDao.insert(sessionLog = sessionLog)
        }
    }

}
