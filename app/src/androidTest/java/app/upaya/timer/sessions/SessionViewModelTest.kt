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
        assert(sessionViewModel.sessionCount.getOrAwaitValue() == 0)
        assert(sessionViewModel.sessionAvg.getOrAwaitValue() == 0f)

        // WHEN a session is added
        sessionRepository.storeSession(Session(length = 2))

        // THEN the corresponding LiveData is updated accordingly
        assert(sessionViewModel.sessionCount.getOrAwaitValue() == 1)
        assert(sessionViewModel.sessionAvg.getOrAwaitValue() == 2f)

        // AND WHEN another session is added
        sessionRepository.storeSession(Session(length = 4))

        // THEN the corresponding LiveData is updated accordingly
        assert(sessionViewModel.sessionCount.getOrAwaitValue() == 2)
        assert(sessionViewModel.sessionAvg.getOrAwaitValue() == 3f)
    }

}
