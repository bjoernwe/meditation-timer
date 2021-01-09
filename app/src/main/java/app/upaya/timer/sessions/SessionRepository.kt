package app.upaya.timer.sessions

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SessionRepository(context: Context) {

    private val sessionDao = SessionDatabase.getInstance(context).sessionDao
    val sessionCount: LiveData<Int> = sessionDao.getSessionCount()
    val sessionAvg: LiveData<Float> = sessionDao.getSessionAvg()

    suspend fun storeSession(session: Session) {
        withContext(Dispatchers.IO) {
            sessionDao.insert(session)
        }
    }

}
