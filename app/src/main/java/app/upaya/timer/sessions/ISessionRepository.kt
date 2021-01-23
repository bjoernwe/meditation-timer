package app.upaya.timer.sessions

import androidx.lifecycle.LiveData


interface ISessionRepository {
    val sessionAvg: LiveData<Float>
    //val sessionAvgOfLastDays: LiveData<List<SessionAvgResult>>
    val sessionCount: LiveData<Int>
    val sessionTotal: LiveData<Int>
    val sessions: LiveData<List<Session>>
    suspend fun storeSession(length: Double)
}
