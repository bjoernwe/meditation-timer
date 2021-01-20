package app.upaya.timer.sessions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SessionRepositoryFakeTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private var sessionRepository = SessionRepositoryFake()

    @Test
    fun sessionLiveDataStatistics() = runBlocking {

        // GIVEN an empty SessionRepository
        assert(sessionRepository.sessionCount.getOrAwaitValue() == 0)
        assert(sessionRepository.sessionAvg.getOrAwaitValue() == 0f)

        // WHEN a session is added
        sessionRepository.storeSession(Session(length = 2))

        // THEN the corresponding LiveData is updated accordingly
        assert(sessionRepository.sessionCount.getOrAwaitValue() == 1)
        assert(sessionRepository.sessionAvg.getOrAwaitValue() == 2f)

        // AND WHEN another session is added
        sessionRepository.storeSession(Session(length = 4))

        // THEN the corresponding LiveData is updated accordingly
        assert(sessionRepository.sessionCount.getOrAwaitValue() == 2)
        assert(sessionRepository.sessionAvg.getOrAwaitValue() == 3f)
    }

}
