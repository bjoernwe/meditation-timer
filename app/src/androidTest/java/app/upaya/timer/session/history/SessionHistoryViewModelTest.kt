package app.upaya.timer.session.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.getOrAwaitValue
import app.upaya.timer.session.SessionLog
import app.upaya.timer.session.SessionLogRepositoryFake
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SessionHistoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val sessionLogRepository = SessionLogRepositoryFake()
    private val sessionHistoryRepository = SessionHistoryRepositoryFake(sessionLogRepository)
    private val sessionHistoryViewModel = SessionHistoryViewModel(sessionHistoryRepository)

    @Test
    fun sessionLiveDataStatistics() = runBlocking {

        // GIVEN a SessionViewModel with an empty SessionRepository
        var sessionAggregate = sessionHistoryViewModel.sessionAggOfAll.getOrAwaitValue()
        assert(sessionAggregate.sessionCount == 0)
        assert(sessionAggregate.avgLength == 0f)
        assert(sessionAggregate.totalLength == 0)

        // WHEN a session is added
        sessionLogRepository.storeSession(SessionLog(length = 2))

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionHistoryViewModel.sessionAggOfAll.getOrAwaitValue()
        assert(sessionAggregate.sessionCount == 1)
        assert(sessionAggregate.avgLength == 2f)
        assert(sessionAggregate.totalLength == 2)

        // AND WHEN another session is added
        sessionLogRepository.storeSession(SessionLog(length = 4))

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionHistoryViewModel.sessionAggOfAll.getOrAwaitValue()
        assert(sessionAggregate.sessionCount == 2)
        assert(sessionAggregate.avgLength == 3f)
        assert(sessionAggregate.totalLength == 6)
    }

}
