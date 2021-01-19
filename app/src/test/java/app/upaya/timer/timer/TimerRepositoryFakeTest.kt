package app.upaya.timer.timer

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*


class TimerRepositoryFakeTest {

    private lateinit var timerRepository: ITimerRepository

    @Before
    fun setUp() {
        timerRepository = TimerRepositoryFake()
    }

    @Test
    fun storeSessionLength() {

        // GIVEN a session length stored through TimerRepository
        val sessionLength = 3.1
        timerRepository.storeSessionLength(sessionLength)

        // WHEN the session length is loaded again
        val loadedSessionLength = timerRepository.loadSessionLength()

        // THEN it is the as before
        assertEquals(sessionLength, loadedSessionLength, 0.001)
    }

}
