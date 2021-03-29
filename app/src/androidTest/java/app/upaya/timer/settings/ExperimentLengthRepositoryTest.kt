package app.upaya.timer.settings

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.upaya.timer.experiments.repositories.length.SessionLengthRepository
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*


class ExperimentLengthRepositoryTest {

    private lateinit var context: Context
    private lateinit var sessionRepository: SessionLengthRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        sessionRepository = SessionLengthRepository(context)
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
