package app.upaya.timer.session.repository

import app.upaya.timer.session.repository.room.SessionLogDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SessionRepository(private val sessionLogDao: SessionLogDao) : ISessionRepository {

    override suspend fun getSessions(): List<SessionLog> {
        return withContext(Dispatchers.IO) {
            return@withContext sessionLogDao.getSessions()
        }
    }

    override suspend fun getLastSession(): SessionLog {
        return withContext(Dispatchers.IO) {
            return@withContext sessionLogDao.getLastSession()
        }
    }

    override suspend fun storeSession(sessionLog: SessionLog) {
        withContext(Dispatchers.IO) {
            sessionLogDao.insert(sessionLog = sessionLog)
        }
    }

}
