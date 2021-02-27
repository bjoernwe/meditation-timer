package app.upaya.timer.session

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*


class SessionHandlerRepositoryFakeTest {

    private lateinit var sessionRepository: ISessionRepository

    @Before
    fun setUp() {
        sessionRepository = SessionRepositoryFake()
    }

    @Test
    fun storeSessionLength() {

        // GIVEN a session length stored through TimerRepository
        val sessionLength = 3.1
        sessionRepository.storeSessionLength(sessionLength)

        // WHEN the session length is loaded again
        val loadedSessionLength = sessionRepository.loadSessionLength()

        // THEN it is the as before
        assertEquals(sessionLength, loadedSessionLength, 0.001)
    }

}
