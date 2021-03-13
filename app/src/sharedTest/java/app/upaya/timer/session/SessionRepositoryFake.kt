package app.upaya.timer.session

import app.upaya.timer.session.repository.ISessionRepository
import app.upaya.timer.session.repository.SessionLog
import java.util.*
import kotlin.collections.ArrayList


class SessionRepositoryFake : ISessionRepository {

    private val sessions: MutableList<SessionLog> = ArrayList()

    override suspend fun getSessions(): List<SessionLog> {
        return Collections.unmodifiableList(sessions)
    }

    override suspend fun getLastSession(): SessionLog? {
        return if (sessions.isEmpty()) null else sessions.last()
    }

    override suspend fun storeSession(sessionLog: SessionLog) {
        sessions.add(sessionLog)
    }

}
