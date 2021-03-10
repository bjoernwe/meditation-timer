package app.upaya.timer.session

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class SessionLogRepositoryFakeTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val sessionRepository = SessionLogRepositoryFake()

    @Test
    fun sessionLiveDataStatistics() = runBlocking {

        // GIVEN an empty SessionLogRepository
        val lastSession = sessionRepository.lastSession.value
        assert(lastSession == null)

        // WHEN a session is stored
        val sessionLog = SessionLog(length = 42)
        sessionRepository.storeSession(sessionLog)

        // THEN the session LiveData updates accordingly
        assert(sessionRepository.lastSession.value == lastSession)

    }

}
