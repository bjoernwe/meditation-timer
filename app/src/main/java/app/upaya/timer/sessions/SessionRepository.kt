package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SessionRepository(sessionDatabase: SessionDatabase) : ISessionRepository {

    private val sessionDao = sessionDatabase.sessionDao

    override val sessionCount: LiveData<Int> = sessionDao.getSessionCount()
    override val sessionAvg: LiveData<Float?> = sessionDao.getSessionAvg()

    override suspend fun storeSession(session: Session) {
        withContext(Dispatchers.IO) {
            sessionDao.insert(session)
        }
    }

}
