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
    fun startCountdownTimerStates() {

        // GIVEN a TimerViewModel with TimerRepository
        val timerRepository = TimerRepositoryFake(1.0)
        val timerViewModel = TimerViewModel(timerRepository, sessionRepository)
        assert(!timerViewModel.isRunning.getOrAwaitValue())
        assert(timerViewModel.state.getOrAwaitValue() == TimerStates.WAITING_FOR_START)

        // WHEN the timer is started
        timerViewModel.startCountdown()

        // THEN its state changes accordingly
        while (!timerViewModel.isRunning.getOrAwaitValue()) Thread.sleep(100)
        assert(timerViewModel.state.getOrAwaitValue() == TimerStates.RUNNING)

        // AND its state changes accordingly when the timer finishes
        while (timerViewModel.isRunning.getOrAwaitValue()) Thread.sleep(100)
        assert(timerViewModel.state.getOrAwaitValue() == TimerStates.FINISHED)
    }

    @Test
    fun startCountdownSecondsRemaining() {

        // GIVEN a TimerViewModel with TimerRepository
        val timerRepository = TimerRepositoryFake(2.0)
        val timerViewModel = TimerViewModel(timerRepository, sessionRepository)
        assert(timerViewModel.secondsRemaining.getOrAwaitValue() == 0)

        // WHEN the timer is started
        timerViewModel.startCountdown()

        // THEN its LiveData changes accordingly
        while (timerViewModel.secondsRemaining.getOrAwaitValue() == 0) Thread.sleep(100)
        assert(timerViewModel.secondsRemaining.getOrAwaitValue() == 2)
        while (timerViewModel.secondsRemaining.getOrAwaitValue() == 2) Thread.sleep(100)
        assert(timerViewModel.secondsRemaining.getOrAwaitValue() == 1)

        // AND its LiveData changes accordingly when the timer finishes
        while (timerViewModel.secondsRemaining.getOrAwaitValue() == 1) Thread.sleep(100)
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
