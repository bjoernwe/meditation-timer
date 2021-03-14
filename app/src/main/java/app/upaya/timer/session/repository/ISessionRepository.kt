package app.upaya.timer.session.repository

import kotlinx.coroutines.flow.Flow


interface ISessionRepository {
    val lastSession: Flow<SessionLog>
    val sessions: Flow<List<SessionLog>>
    fun storeSession(sessionLog: SessionLog)
}
