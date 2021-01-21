package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SessionRepository(sessionDatabase: SessionDatabase) : ISessionRepository {

    private val sessionDao = sessionDatabase.sessionDao

    override val sessionAvg: LiveData<Float> = Transformations.map(sessionDao.getAvg()) { avg -> avg ?: 0f }
    //override val sessionAvgOfLastDays: LiveData<List<SessionAvgResult>> = sessionDao.getAvgOfLastDays()
    override val sessionCount: LiveData<Int> = sessionDao.getCount()
    override val sessionTotal: LiveData<Int> = sessionDao.getTotalLength()
    override val sessions: LiveData<List<Session>> = sessionDao.getSessions()

    override suspend fun storeSession(session: Session) {
        withContext(Dispatchers.IO) {
            sessionDao.insert(session)
        }
    }

}
