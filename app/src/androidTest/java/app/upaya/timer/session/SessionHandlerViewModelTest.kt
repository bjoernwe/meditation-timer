package app.upaya.timer.session

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.getOrAwaitValue
import app.upaya.timer.session_history.SessionHistoryRepositoryFake
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SessionHandlerViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Make coroutines synchronous

    private val sessionRepository = SessionHistoryRepositoryFake()

    @Test
    fun startCountdownTimerStates() {

        // GIVEN a TimerViewModel with TimerRepository
        val timerRepository = SessionLengthRepositoryFake(1.0)
        val timerViewModel = SessionViewModel(timerRepository, sessionRepository)
        assert(!timerViewModel.isRunning.getOrAwaitValue())
        assert(timerViewModel.state.getOrAwaitValue() is Idle)

        // WHEN the timer is started
        (timerViewModel.state.getOrAwaitValue() as Idle).startSession()

        // THEN its state changes accordingly
        while (!timerViewModel.isRunning.getOrAwaitValue()) Thread.sleep(100)
        assert(timerViewModel.state.getOrAwaitValue() is Running)

        // AND its state changes accordingly when the timer finishes
        while (timerViewModel.isRunning.getOrAwaitValue()) Thread.sleep(100)
        assert(timerViewModel.state.getOrAwaitValue() is Finished)
    }

    @Test
    fun increaseSessionLength() {

        // GIVEN a TimerViewModel with TimerRepository
        val initialSessionLength = 1.0
        val timerRepository = SessionLengthRepositoryFake(initialSessionLength)
        val timerViewModel = SessionViewModel(timerRepository, sessionRepository)
        assert(timerViewModel.sessionLength.getOrAwaitValue() == initialSessionLength)
        Assert.assertEquals(timerRepository.loadSessionLength(), initialSessionLength, 0.001)

        // WHEN the session length is increased for the finished timer
        (timerViewModel.state.getOrAwaitValue() as Idle).startSession()
        while (timerViewModel.state.getOrAwaitValue() is Running) Thread.sleep(100)
        (timerViewModel.state.getOrAwaitValue() as Finished).increaseSessionLength()

        // THEN its LiveData is increased
        assert(timerViewModel.sessionLength.getOrAwaitValue() > initialSessionLength)

        // AND the repository has stored the new value
        assert(timerRepository.loadSessionLength() > initialSessionLength)
    }

    @Test
    fun decreaseSessionLength() {

        // GIVEN a TimerViewModel with TimerRepository
        val initialSessionLength = 2.0
        val timerRepository = SessionLengthRepositoryFake(initialSessionLength)
        val timerViewModel = SessionViewModel(timerRepository, sessionRepository)
        assert(timerViewModel.sessionLength.getOrAwaitValue() == initialSessionLength)
        Assert.assertEquals(timerRepository.loadSessionLength(), initialSessionLength, 0.001)

        // WHEN the session length is decreased for the finished timer
        (timerViewModel.state.getOrAwaitValue() as Idle).startSession()
        while (timerViewModel.state.getOrAwaitValue() is Running) Thread.sleep(100)
        (timerViewModel.state.getOrAwaitValue() as Finished).decreaseSessionLength()

        // THEN its LiveData is decreased
        assert(timerViewModel.sessionLength.getOrAwaitValue() < initialSessionLength)

        // AND the repository has stored the new value
        assert(timerRepository.loadSessionLength() < initialSessionLength)
    }

}
