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
        var sessionAggregate = sessionStatsRepository.sessionAggregate.first()
        assert(sessionAggregate.sessionCount == 0)
        assert(sessionAggregate.avgLength == null)
        assert(sessionAggregate.totalLength == null)

        // WHEN a session is added
        sessionLogRepository.storeSession(generateSessionLog(length = 2))

        // THEN the corresponding Flow is updated accordingly
        sessionAggregate = sessionStatsRepository.sessionAggregate.first()
        assertEquals(2.0, sessionAggregate.avgLength!!, 0.001)
        assert(sessionAggregate.sessionCount == 1)
        assert(sessionAggregate.totalLength == 2)

        // AND WHEN another session is added
        sessionLogRepository.storeSession(generateSessionLog(length = 4))

        // THEN the corresponding Flow is updated accordingly
        sessionAggregate = sessionStatsRepository.sessionAggregate.first()
        assertEquals(3.0, sessionAggregate.avgLength!!, 0.001)
        assert(sessionAggregate.sessionCount == 2)
        assert(sessionAggregate.totalLength == 6)
    }

}
