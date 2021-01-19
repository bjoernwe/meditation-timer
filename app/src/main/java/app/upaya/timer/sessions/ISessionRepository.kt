package app.upaya.timer.sessions

import androidx.lifecycle.LiveData


interface ISessionRepository {
    val sessionCount: LiveData<Int>
    val sessionAvg: LiveData<Float?>
    suspend fun storeSession(session: Session)
}
