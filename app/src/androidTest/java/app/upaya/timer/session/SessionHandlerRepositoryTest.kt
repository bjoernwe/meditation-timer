package app.upaya.timer.session

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*


class SessionHandlerRepositoryTest {

    private lateinit var context: Context
    private lateinit var sessionRepository: SessionRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        sessionRepository = SessionRepository(context)
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
