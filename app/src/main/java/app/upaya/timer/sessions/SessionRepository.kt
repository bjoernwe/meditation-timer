package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SessionRepository(sessionDatabase: SessionDatabase) : ISessionRepository {

    private val sessionDao = sessionDatabase.sessionDao

    val avg: LiveData<Float?> = sessionDao.getAvg()
    val avgOfLastDays: LiveData<List<SessionAvgResult>> = sessionDao.getAvgOfLastDays()
    override val sessionCount: LiveData<Int> = sessionDao.getSessionCount()
    override val sessionAvg: LiveData<Float> = Transformations.map(sessionDao.getSessionAvg()) { avg -> avg ?: 0f }

    override suspend fun storeSession(session: Session) {
        withContext(Dispatchers.IO) {
            sessionDao.insert(session)
        }
    }

}
