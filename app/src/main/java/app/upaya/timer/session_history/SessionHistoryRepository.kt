package app.upaya.timer.session_history

import androidx.lifecycle.LiveData
import app.upaya.timer.session_history.room_entries.SessionEntryDatabase
import app.upaya.timer.session_history.room_entries.SessionEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


class SessionHistoryRepository(sessionEntryDatabase: SessionEntryDatabase) : ISessionHistoryRepository {

    private val sessionDao = sessionEntryDatabase.sessionEntryDao

    private val numberOfAggregatedDays = 14

    override val sessionAggregateOfAll = sessionDao.getAggregateOfAll()
    override val sessionAggregateOfLastDays = sessionDao.getAggregateOfLastDays(numberOfAggregatedDays)
    override val sessions: LiveData<List<SessionEntry>> = sessionDao.getSessions()

    override suspend fun storeSession(length: Double, endDate: Date) {
        withContext(Dispatchers.IO) {
            sessionDao.insert(SessionEntry(length = length.toInt(), endDate = endDate))
        }
    }

}
