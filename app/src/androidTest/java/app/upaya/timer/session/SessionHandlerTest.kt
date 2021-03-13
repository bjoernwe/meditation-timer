package app.upaya.timer.session

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.upaya.timer.getOrAwaitValue
import org.junit.Rule
import org.junit.Test


class SessionHandlerTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Make coroutines synchronous

    @Test
    fun onSessionFinished() {

        // GIVEN a SessionHandler
        val sessionLogRepository: ISessionLogRepository = SessionLogRepositoryFake()
        val sessionHandler = SessionHandler(
            sessionLogRepository = sessionLogRepository,
            initialSessionLength = 42.0
        )

        // WHEN onSessionFinished is called
        val finishedSession = SessionLog(length = sessionHandler.sessionLength.toInt())
        sessionHandler.onSessionFinished()

        // THEN the session is stored
        val storedSession = sessionLogRepository.lastSession.getOrAwaitValue()
        assert(storedSession == finishedSession)

    }

    @Test
    fun onRatingSubmittedUp() {

        // GIVEN a SessionHandler
        val initialSessionLength = 42.0
        val sessionLogRepository: ISessionLogRepository = SessionLogRepositoryFake()
        val sessionHandler = SessionHandler(
            sessionLogRepository = sessionLogRepository,
            initialSessionLength = initialSessionLength
        )

        // WHEN onRatingSubmitted is called with UP
        sessionHandler.onRatingSubmitted(rating = SessionRating.UP)

        // THEN the current session length is decreased
        assert(sessionHandler.sessionLength < initialSessionLength)

    }

    @Test
    fun onRatingSubmittedDown() {

        // GIVEN a SessionHandler
        val initialSessionLength = 42.0
        val sessionLogRepository: ISessionLogRepository = SessionLogRepositoryFake()
        val sessionHandler = SessionHandler(
            sessionLogRepository = sessionLogRepository,
            initialSessionLength = initialSessionLength
        )

        // WHEN onRatingSubmitted is called with UP
        sessionHandler.onRatingSubmitted(rating = SessionRating.DOWN)

        // THEN the current session length is decreased
        assert(sessionHandler.sessionLength > initialSessionLength)

    }

}
