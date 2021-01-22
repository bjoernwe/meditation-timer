package app.upaya.timer.sessions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class SessionRepositoryFakeTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val sessionRepository = SessionRepositoryFake()

    @Test
    fun sessionLiveDataStatistics() = runBlocking {

        // GIVEN an empty SessionRepository
        assert(sessionRepository.sessionAvg.getOrAwaitValue() == 0f)
        assert(sessionRepository.sessionCount.getOrAwaitValue() == 0)
        assert(sessionRepository.sessionTotal.getOrAwaitValue() == 0)

        // WHEN a session is added
        sessionRepository.storeSession(Session(length = 2, endDate = Date(1000L)))

        // THEN the corresponding LiveData is updated accordingly
        assert(sessionRepository.sessionAvg.getOrAwaitValue() == 2f)
        assert(sessionRepository.sessionCount.getOrAwaitValue() == 1)
        assert(sessionRepository.sessionTotal.getOrAwaitValue() == 2)

        // AND WHEN another session is added
        sessionRepository.storeSession(Session(length = 4, endDate = Date(2000L)))

        // THEN the corresponding LiveData is updated accordingly
        assert(sessionRepository.sessionAvg.getOrAwaitValue() == 3f)
        assert(sessionRepository.sessionCount.getOrAwaitValue() == 2)
        assert(sessionRepository.sessionTotal.getOrAwaitValue() == 6)

        // AND the sessions are ordered descendingly for time
        val session0 = sessionRepository.sessions.getOrAwaitValue()[0]
        val session1 = sessionRepository.sessions.getOrAwaitValue()[1]
        assert(session0.endDate > session1.endDate)
    }

}
