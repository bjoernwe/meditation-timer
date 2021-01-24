package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import java.util.*


interface ISessionRepository {
    val sessionAggregateOfAll: LiveData<SessionAggregate>
    val sessionAggregateOfLastDays: LiveData<List<SessionAggregate>>
    val sessions: LiveData<List<Session>>
    suspend fun storeSession(length: Double, endDate: Date = Date())
}
