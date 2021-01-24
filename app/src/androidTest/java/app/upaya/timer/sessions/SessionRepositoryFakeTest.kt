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
        var sessionAggregate = sessionRepository.sessionAggregateOfAll.getOrAwaitValue()
        assert(sessionAggregate.avg_length == 0f)
        assert(sessionAggregate.session_count == 0)
        assert(sessionAggregate.total_length == 0)

        // WHEN a session is added
        sessionRepository.storeSession(length = 2.0, endDate = Date(1000L))

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionRepository.sessionAggregateOfAll.getOrAwaitValue()
        assert(sessionAggregate.avg_length == 2f)
        assert(sessionAggregate.session_count == 1)
        assert(sessionAggregate.total_length == 2)

        // AND WHEN another session is added
        sessionRepository.storeSession(length = 4.0, endDate = Date(2000L))

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionRepository.sessionAggregateOfAll.getOrAwaitValue()
        assert(sessionAggregate.avg_length == 3f)
        assert(sessionAggregate.session_count == 2)
        assert(sessionAggregate.total_length == 6)

        // AND the sessions are ordered descendingly for time
        val session0 = sessionRepository.sessions.getOrAwaitValue()[0]
        val session1 = sessionRepository.sessions.getOrAwaitValue()[1]
        assert(session0.endDate > session1.endDate)
    }

}
