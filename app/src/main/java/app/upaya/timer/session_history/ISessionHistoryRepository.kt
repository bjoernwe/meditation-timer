package app.upaya.timer.session_history

import androidx.lifecycle.LiveData
import app.upaya.timer.session_history.room_entries.SessionEntry
import java.util.*


interface ISessionHistoryRepository {
    val sessionAggregateOfAll: LiveData<SessionAggregate>
    val sessionAggregateOfLastDays: LiveData<List<SessionAggregate>>
    val sessions: LiveData<List<SessionEntry>>
    suspend fun storeSession(length: Double, endDate: Date = Date())
}
