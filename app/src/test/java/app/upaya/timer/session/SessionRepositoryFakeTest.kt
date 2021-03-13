package app.upaya.timer.session

import app.upaya.timer.session.repository.SessionLog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Test


class SessionRepositoryFakeTest {

    @Test
    fun storeSession() = runBlocking {

        // GIVEN an empty SessionLogRepository
        val sessionRepository = SessionRepositoryFake()
        assert(sessionRepository.sessions.first().isEmpty())

        // WHEN a session is stored
        val sessionLog = SessionLog(length = 42)
        sessionRepository.storeSession(sessionLog)

        // THEN the session FlowState updates accordingly
        assert(sessionRepository.sessions.take(1).first()[0] == sessionLog)

    }

}
