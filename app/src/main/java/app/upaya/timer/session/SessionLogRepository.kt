package app.upaya.timer.session

import androidx.lifecycle.LiveData
import app.upaya.timer.session.room.SessionLogDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SessionLogRepository(sessionLogDatabase: SessionLogDatabase) : ISessionLogRepository {

    private val sessionDao = sessionLogDatabase.sessionLogDao

    override val lastSession: LiveData<SessionLog> = sessionDao.getLastSession()

    override suspend fun storeSession(sessionLog: SessionLog) {
        withContext(Dispatchers.IO) {
            sessionDao.insert(sessionLog = sessionLog)
        }
    }

}
