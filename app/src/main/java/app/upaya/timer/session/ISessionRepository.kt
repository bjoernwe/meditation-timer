package app.upaya.timer.session


interface ISessionRepository {
    suspend fun getSessions(): List<SessionLog>
    suspend fun getLastSession(): SessionLog?
    suspend fun storeSession(sessionLog: SessionLog)
}
