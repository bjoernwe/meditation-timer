package app.upaya.timer.session.repository


interface ISessionRepository {
    suspend fun getSessions(): List<SessionLog>
    suspend fun getLastSession(): SessionLog?
    suspend fun storeSession(sessionLog: SessionLog)
}
