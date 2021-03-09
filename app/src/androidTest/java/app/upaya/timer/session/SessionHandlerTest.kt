package app.upaya.timer.session

import org.junit.Test


class SessionHandlerTest {

    @Test
    fun onSessionFinished() {

        // GIVEN a SessionHandler
        val sessionLogRepository: ISessionLogRepository = SessionLogRepositoryFake()
        val sessionHandler = SessionHandler(sessionLogRepository = sessionLogRepository)

        // WHEN onSessionFinished is called
        val finishedSession = SessionLog(length = 42)
        sessionHandler.onSessionFinished(sessionLog = finishedSession)

        // THEN the session is stored
        val storedSession = sessionLogRepository.lastSession.value
        assert(storedSession == finishedSession)

    }

    @Test
    fun onRatingSubmittedUp() {

        // GIVEN a SessionHandler
        val sessionLogRepository: ISessionLogRepository = SessionLogRepositoryFake()
        val sessionHandler = SessionHandler(sessionLogRepository = sessionLogRepository)

        // WHEN onRatingSubmitted is called with UP
        val currentSessionLength = 42.0
        val newSessionLength = sessionHandler.onRatingSubmitted(
            rating = SessionRating.UP,
            currentSessionLength = currentSessionLength
        )

        // THEN the current session length is decreased
        assert(newSessionLength < currentSessionLength)

    }

    @Test
    fun onRatingSubmittedDown() {

        // GIVEN a SessionHandler
        val sessionLogRepository: ISessionLogRepository = SessionLogRepositoryFake()
        val sessionHandler = SessionHandler(sessionLogRepository = sessionLogRepository)

        // WHEN onRatingSubmitted is called with UP
        val currentSessionLength = 42.0
        val newSessionLength = sessionHandler.onRatingSubmitted(
            rating = SessionRating.DOWN,
            currentSessionLength = currentSessionLength
        )

        // THEN the current session length is decreased
        assert(newSessionLength > currentSessionLength)

    }

}
