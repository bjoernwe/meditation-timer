package app.upaya.timer.session

import androidx.lifecycle.LiveData
import app.upaya.timer.session.room.SessionLogDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


class SessionRepository(sessionLogDatabase: SessionLogDatabase) : ISessionRepository {

    private val sessionDao = sessionLogDatabase.sessionLogDao

    override val sessions: LiveData<List<SessionLog>> = sessionDao.getSessions()

    override suspend fun storeSession(length: Double, endDate: Date) {
        withContext(Dispatchers.IO) {
            sessionDao.insert(SessionLog(length = length.toInt(), endDate = endDate))
        }
    }

}
