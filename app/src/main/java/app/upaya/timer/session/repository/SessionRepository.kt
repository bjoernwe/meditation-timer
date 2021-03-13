package app.upaya.timer.session.repository

import app.upaya.timer.session.repository.room.SessionLogDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SessionRepository(private val sessionLogDatabase: SessionLogDatabase) : ISessionRepository {

    override suspend fun getSessions(): List<SessionLog> {
        return withContext(Dispatchers.IO) {
            return@withContext sessionLogDatabase.sessionLogDao.getSessions()
        }
    }

    override suspend fun getLastSession(): SessionLog {
        return withContext(Dispatchers.IO) {
            return@withContext sessionLogDatabase.sessionLogDao.getLastSession()
        }
    }

    override suspend fun storeSession(sessionLog: SessionLog) {
        withContext(Dispatchers.IO) {
            sessionLogDatabase.sessionLogDao.insert(sessionLog = sessionLog)
        }
    }

}
