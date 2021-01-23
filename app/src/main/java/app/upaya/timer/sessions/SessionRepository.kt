package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import app.upaya.timer.sessions.room.SessionDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


class SessionRepository(sessionDatabase: SessionDatabase) : ISessionRepository {

    private val sessionDao = sessionDatabase.sessionDao

    override val sessionAvg: LiveData<Float> = Transformations.map(sessionDao.getAvg()) { avg -> avg ?: 0f }
    //override val sessionAvgOfLastDays: LiveData<List<SessionAvgResult>> = sessionDao.getAvgOfLastDays()
    override val sessionCount: LiveData<Int> = sessionDao.getCount()
    override val sessionTotal: LiveData<Int> = Transformations.map(sessionDao.getTotalLength()) { total -> total ?: 0 }
    override val sessions: LiveData<List<Session>> = sessionDao.getSessions()

    override suspend fun storeSession(length: Double, endDate: Date) {
        withContext(Dispatchers.IO) {
            sessionDao.insert(Session(length = length.toInt(), endDate = endDate))
        }
    }

}
