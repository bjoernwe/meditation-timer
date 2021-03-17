package app.upaya.timer.session

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.upaya.timer.session.creator.ISessionCreator
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class SessionStateTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Make coroutines synchronous

    @Test
    fun sessionStateTransitions() = runBlocking {

        // GIVEN a SessionState object
        val onSessionFinishedCalls: MutableList<Date> = ArrayList()
        val onRatingSubmittedCalls: MutableList<OnRatingCallArgs> = ArrayList()
        val sessionCreator: ISessionCreator = SessionCreatorMock(
            onSessionFinishedCalls = onSessionFinishedCalls,
            onRatingSubmittedCalls = onRatingSubmittedCalls,
            initialSessionLength = 2.0
        )
        val state: StateFlow<SessionState?> = SessionState.create(sessionCreator = sessionCreator)

        // WHEN a session is started
        (state.first() as Idle).startSession()

        // THEN the state moves from Idle to Running
        assert(state.first() is Running)

        // AND WHEN one second has passed
        Thread.sleep(1000)

        // THEN the state is still Running
        assert(state.first() is Running)

        // AND WHEN more time has passed
        Thread.sleep(1500)

        // THEN the state is Finished
        assert(state.first() is Finished)

        // AND WHEN the finished session is rated
        (state.first() as Finished).rateSession(SessionRating.DOWN)

        // THEN the new state is Idle again
        assert(state.first() is Idle)
    }

}
