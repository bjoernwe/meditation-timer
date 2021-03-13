package app.upaya.timer.session

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.getOrAwaitValue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.collections.ArrayList


@RunWith(AndroidJUnit4::class)
class SessionStateTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Make coroutines synchronous

    @Test
    fun sessionStateTransitions() {

        // GIVEN a SessionState object
        val onSessionFinishedCalls: MutableList<Date> = ArrayList()
        val onRatingSubmittedCalls: MutableList<OnRatingCallArgs> = ArrayList()
        val sessionHandler: ISessionHandler = SessionHandlerMock(
            onSessionFinishedCalls = onSessionFinishedCalls,
            onRatingSubmittedCalls = onRatingSubmittedCalls,
            initialSessionLength = 2.0
        )
        val state = SessionState.create(sessionHandler = sessionHandler)

        // WHEN a session is started
        (state.getOrAwaitValue() as Idle).startSession()

        // THEN the state moves from Idle to Running
        assert(state.getOrAwaitValue() is Running)

        // AND WHEN one second has passed
        Thread.sleep(1000)

        // THEN the state is still Running
        assert(state.getOrAwaitValue() is Running)

        // AND WHEN more time has passed
        Thread.sleep(1500)

        // THEN the state is Finished
        assert(state.getOrAwaitValue() is Finished)

        // AND WHEN the finished session is rated
        (state.getOrAwaitValue() as Finished).rateSession(SessionRating.DOWN)

        // THEN the new state is Idle again
        assert(state.getOrAwaitValue() is Idle)
    }

}
