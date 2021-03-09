package app.upaya.timer.session

import androidx.lifecycle.LiveData


interface ISessionLogRepository {
    val lastSession: LiveData<SessionLog>
    suspend fun storeSession(sessionLog: SessionLog)
}
