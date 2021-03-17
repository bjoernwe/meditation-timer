package app.upaya.timer.session.stats

import app.upaya.timer.session.SessionRepositoryFake
import app.upaya.timer.session.repository.SessionLog
import app.upaya.timer.session.repository.stats.SessionStatsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*


class SessionStatsRepositoryTest {

    private val sessionLogRepository = SessionRepositoryFake()
    private val sessionStatsRepository = SessionStatsRepository(sessionLogRepository)

    @Test
    fun sessionLiveDataStatistics() = runBlocking {

        // GIVEN an empty SessionRepository
        var sessionAggregate = sessionStatsRepository.sessionAggregate.first()
        assert(sessionAggregate.sessionCount == 0)
        assert(sessionAggregate.avgLength == null)
        assert(sessionAggregate.totalLength == null)

        // WHEN a session is added
        sessionLogRepository.storeSession(SessionLog(length = 2, endDate = Date(1000L)))

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionStatsRepository.sessionAggregate.first()
        assert(sessionAggregate.avgLength == 2f)
        assert(sessionAggregate.sessionCount == 1)
        assert(sessionAggregate.totalLength == 2)

        // AND WHEN another session is added
        sessionLogRepository.storeSession(SessionLog(length = 4, endDate = Date(2000L)))

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionStatsRepository.sessionAggregate.first()
        assert(sessionAggregate.avgLength == 3f)
        assert(sessionAggregate.sessionCount == 2)
        assert(sessionAggregate.totalLength == 6)
    }

}
