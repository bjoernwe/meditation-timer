package app.upaya.timer.session.repository

import kotlinx.coroutines.flow.Flow


interface ISessionRepository {
    val lastSession: Flow<SessionLog>
    val sessions: Flow<List<SessionLog>>
    suspend fun storeSession(sessionLog: SessionLog)
}
