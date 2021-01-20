package app.upaya.timer.timer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.getOrAwaitValue
import app.upaya.timer.sessions.SessionRepositoryFake
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TimerViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Make coroutines synchronous

    private val sessionRepository = SessionRepositoryFake()

    @Test
    fun startCountdown() {

        // GIVEN a TimerViewModel with TimerRepository
        val timerRepository = TimerRepositoryFake(1.0)
        val timerViewModel = TimerViewModel(timerRepository, sessionRepository)
        assert(timerViewModel.isRunning.getOrAwaitValue() == false)
        assert(timerViewModel.state.getOrAwaitValue() == TimerStates.WAITING_FOR_START)

        // WHEN the timer is started
        timerViewModel.startCountdown()

        // THEN its state and LiveData change accordingly
        while (!timerViewModel.isRunning.getOrAwaitValue()) Thread.sleep(100)
        assert(timerViewModel.state.getOrAwaitValue() == TimerStates.RUNNING)
        assert(timerViewModel.secondsRemaining.getOrAwaitValue() == 1)

        // AND its state and LiveData change accordingly when the timer finishes
        while (timerViewModel.isRunning.getOrAwaitValue()) Thread.sleep(100)
        assert(timerViewModel.state.getOrAwaitValue() == TimerStates.WAITING_FOR_START)
        assert(timerViewModel.secondsRemaining.getOrAwaitValue() == 0)
    }

    @Test
    fun increaseSessionLength() {

        // GIVEN a TimerViewModel with TimerRepository
        val initialSessionLength = 2.0
        val timerRepository = TimerRepositoryFake(initialSessionLength)
        val timerViewModel = TimerViewModel(timerRepository, sessionRepository)
        assert(timerViewModel.sessionLength.getOrAwaitValue() == initialSessionLength)
        Assert.assertEquals(timerRepository.loadSessionLength(), initialSessionLength, 0.001)

        // WHEN the session length is increased
        timerViewModel.increaseSessionLength()

        // THEN its LiveData is increased
        assert(timerViewModel.sessionLength.getOrAwaitValue() > initialSessionLength)

        // AND the repository has stored the new value
        assert(timerRepository.loadSessionLength() > initialSessionLength)
    }

    @Test
    fun decreaseSessionLength() {

        // GIVEN a TimerViewModel with TimerRepository
        val initialSessionLength = 2.0
        val timerRepository = TimerRepositoryFake(initialSessionLength)
        val timerViewModel = TimerViewModel(timerRepository, sessionRepository)
        assert(timerViewModel.sessionLength.getOrAwaitValue() == initialSessionLength)
        Assert.assertEquals(timerRepository.loadSessionLength(), initialSessionLength, 0.001)

        // WHEN the session length is increased
        timerViewModel.decreaseSessionLength()

        // THEN its LiveData is decreased
        assert(timerViewModel.sessionLength.getOrAwaitValue() < initialSessionLength)

        // AND the repository has stored the new value
        assert(timerRepository.loadSessionLength() < initialSessionLength)
    }

}
