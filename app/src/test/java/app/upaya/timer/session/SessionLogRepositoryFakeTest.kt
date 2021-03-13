package app.upaya.timer.session

import kotlinx.coroutines.runBlocking
import org.junit.Test


class SessionLogRepositoryFakeTest {

    @Test
    fun storeSession() = runBlocking {

        // GIVEN an empty SessionLogRepository
        val sessionRepository = SessionLogRepositoryFake()
        assert(sessionRepository.getLastSession() == null)

        // WHEN a session is stored
        val sessionLog = SessionLog(length = 42)
        sessionRepository.storeSession(sessionLog)

        // THEN the session LiveData updates accordingly
        assert(sessionRepository.getLastSession() == sessionLog)

    }

}