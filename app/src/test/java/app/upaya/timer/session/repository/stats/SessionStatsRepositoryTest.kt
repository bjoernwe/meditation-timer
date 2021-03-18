package app.upaya.timer.session.repository.stats

import app.upaya.timer.session.repository.SessionRepositoryFake
import app.upaya.timer.session.repository.SessionLog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*


class SessionStatsRepositoryTest {

    private val sessionLogRepository = SessionRepositoryFake()
    private val sessionStatsRepository = SessionStatsRepository(sessionLogRepository)

    private fun generateSessionLog(length: Int) : SessionLog {
        return SessionLog(
            hint = UUID.randomUUID(),
            startDate = Date(0L),
            endDate = Date(length * 1000L)
        )
    }

    @Test
    fun sessionLiveDataStatistics() = runBlocking {

        // GIVEN an empty SessionRepository
        var sessionStats = sessionStatsRepository.sessionStats.first()
        assert(sessionStats.sessionCount == 0)
        assert(sessionStats.avgLength == null)
        assert(sessionStats.totalLength == null)

        // WHEN a session is added
        sessionLogRepository.storeSession(generateSessionLog(length = 2))

        // THEN the corresponding Flow is updated accordingly
        sessionStats = sessionStatsRepository.sessionStats.first()
        assertEquals(2.0, sessionStats.avgLength!!, 0.001)
        assert(sessionStats.sessionCount == 1)
        assert(sessionStats.totalLength == 2)

        // AND WHEN another session is added
        sessionLogRepository.storeSession(generateSessionLog(length = 4))

        // THEN the corresponding Flow is updated accordingly
        sessionStats = sessionStatsRepository.sessionStats.first()
        assertEquals(3.0, sessionStats.avgLength!!, 0.001)
        assert(sessionStats.sessionCount == 2)
        assert(sessionStats.totalLength == 6)
    }

}
