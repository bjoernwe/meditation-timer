package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import java.util.*


interface ISessionRepository {
    val sessionAvg: LiveData<Float>
    //val sessionAvgOfLastDays: LiveData<List<SessionAvgResult>>
    val sessionCount: LiveData<Int>
    val sessionTotal: LiveData<Int>
    val sessions: LiveData<List<Session>>
    suspend fun storeSession(length: Double, endDate: Date = Date())
}
