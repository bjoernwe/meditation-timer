package app.upaya.timer.sessions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SessionViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val sessionRepository = SessionRepositoryFake()
    private val sessionViewModel = SessionViewModel(sessionRepository)

    @Test
    fun sessionLiveDataStatistics() = runBlocking {

        // GIVEN a SessionViewModel with an empty SessionRepository
        var sessionAggregate = sessionViewModel.sessionAggOfAll.getOrAwaitValue()
        assert(sessionAggregate.session_count == 0)
        assert(sessionAggregate.avg_length == 0f)
        assert(sessionAggregate.total_length == 0)

        // WHEN a session is added
        sessionRepository.storeSession(length = 2.0)

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionViewModel.sessionAggOfAll.getOrAwaitValue()
        assert(sessionAggregate.session_count == 1)
        assert(sessionAggregate.avg_length == 2f)
        assert(sessionAggregate.total_length == 2)

        // AND WHEN another session is added
        sessionRepository.storeSession(length = 4.0)

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionViewModel.sessionAggOfAll.getOrAwaitValue()
        assert(sessionAggregate.session_count == 2)
        assert(sessionAggregate.avg_length == 3f)
        assert(sessionAggregate.total_length == 6)
    }

}
