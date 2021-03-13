package app.upaya.timer.session

import kotlinx.coroutines.runBlocking
import org.junit.Test


class SessionHandlerTest {

    @Test
    fun onSessionFinished() = runBlocking {

        // GIVEN a SessionHandler
        val sessionRepository: ISessionRepository = SessionRepositoryFake()
        val sessionHandler = SessionHandler(
            sessionRepository = sessionRepository,
            initialSessionLength = 42.0
        )

        // WHEN onSessionFinished is called
        val finishedSession = SessionLog(length = sessionHandler.sessionLength.toInt())
        sessionHandler.onSessionFinished()

        // THEN the session is stored
        val storedSession = (sessionRepository as SessionRepositoryFake).getLastSession()
        assert(storedSession == finishedSession)

    }

    @Test
    fun onRatingSubmittedUp() {

        // GIVEN a SessionHandler
        val initialSessionLength = 42.0
        val sessionRepository: ISessionRepository = SessionRepositoryFake()
        val sessionHandler = SessionHandler(
            sessionRepository = sessionRepository,
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
        val sessionRepository: ISessionRepository = SessionRepositoryFake()
        val sessionHandler = SessionHandler(
            sessionRepository = sessionRepository,
            initialSessionLength = initialSessionLength
        )

        // WHEN onRatingSubmitted is called with UP
        sessionHandler.onRatingSubmitted(rating = SessionRating.DOWN)

        // THEN the current session length is decreased
        assert(sessionHandler.sessionLength > initialSessionLength)

    }

}
