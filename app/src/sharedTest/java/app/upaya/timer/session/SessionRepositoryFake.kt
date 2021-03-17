package app.upaya.timer.session

import app.upaya.timer.session.repository.ISessionRepository
import app.upaya.timer.session.repository.SessionLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.*


class SessionRepositoryFake : ISessionRepository {

    private val _sessions: MutableStateFlow<ArrayList<SessionLog>> = MutableStateFlow(ArrayList())

    override val sessions: Flow<List<SessionLog>> = _sessions

    override val lastSession: Flow<SessionLog> = _sessions.map { it.last() }

    override fun storeSession(sessionLog: SessionLog) {
        _sessions.value.add(sessionLog)
        _sessions.value = _sessions.value
    }

}
