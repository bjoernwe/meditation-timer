package app.upaya.timer.sessions

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SessionRepository(application: Application) {

    private val sessionDao = SessionDatabase.getInstance(application).sessionDao
    val sessionCount: LiveData<Int> = sessionDao.getSessionCount()
    val sessionAvg: LiveData<Float> = sessionDao.getSessionAvg()

    suspend fun storeSession(session: Session) {
        withContext(Dispatchers.IO) {
            sessionDao.insert(session)
        }
    }

}
