package app.upaya.timer.session

import app.upaya.timer.session.room.SessionLogDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SessionRepository(private val sessionLogDatabase: SessionLogDatabase) : ISessionRepository {

    override suspend fun getSessions(): List<SessionLog> {
        return sessionLogDatabase.sessionLogDao.getSessions()
    }

    override suspend fun getLastSession(): SessionLog {
        return sessionLogDatabase.sessionLogDao.getLastSession()
    }

    override suspend fun storeSession(sessionLog: SessionLog) {
        withContext(Dispatchers.IO) {
            sessionLogDatabase.sessionLogDao.insert(sessionLog = sessionLog)
        }
    }

}
