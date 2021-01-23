package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import java.util.*


interface ISessionRepository {
    val sessionAggregateOfLastDays: LiveData<List<SessionAggregate>>
    val sessionAvg: LiveData<Float>
    val sessionCount: LiveData<Int>
    val sessionTotal: LiveData<Int>
    val sessions: LiveData<List<Session>>
    suspend fun storeSession(length: Double, endDate: Date = Date())
}
