package app.upaya.timer.session

import app.upaya.timer.settings.ISessionLengthRepository
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*


class SessionLengthRepositoryFakeTest {

    private lateinit var sessionLengthRepository: ISessionLengthRepository

    @Before
    fun setUp() {
        sessionLengthRepository = SessionLengthRepositoryFake()
    }

    @Test
    fun storeSessionLength() {

        // GIVEN a session length stored through TimerRepository
        val sessionLength = 3.1
        sessionLengthRepository.storeSessionLength(sessionLength)

        // WHEN the session length is loaded again
        val loadedSessionLength = sessionLengthRepository.loadSessionLength()

        // THEN it is the as before
        assertEquals(sessionLength, loadedSessionLength, 0.001)
    }

}
