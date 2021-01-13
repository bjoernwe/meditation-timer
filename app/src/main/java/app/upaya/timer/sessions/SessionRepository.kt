package app.upaya.timer.sessions

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SessionRepository(context: Context) {

    private val sessionDao = SessionDatabase.getInstance(context).sessionDao
    val avg: LiveData<Float?> = sessionDao.getAvg()
    val avgOfLastDays: LiveData<List<SessionAvgResult>> = sessionDao.getAvgOfLastDays()
    val count: LiveData<Int> = sessionDao.getCount()
    val sessions: LiveData<List<Session>> = sessionDao.getSessions(100)

    suspend fun storeSession(session: Session) {
        withContext(Dispatchers.IO) {
            sessionDao.insert(session)
        }
    }

}
