package app.upaya.timer.session

import androidx.lifecycle.LiveData
import app.upaya.timer.session.room.SessionEntryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


class SessionRepository(sessionEntryDatabase: SessionEntryDatabase) : ISessionRepository {

    private val sessionDao = sessionEntryDatabase.sessionEntryDao

    override val sessions: LiveData<List<SessionDetails>> = sessionDao.getSessions()

    override suspend fun storeSession(length: Double, endDate: Date) {
        withContext(Dispatchers.IO) {
            sessionDao.insert(SessionDetails(length = length.toInt(), endDate = endDate))
        }
    }

}
