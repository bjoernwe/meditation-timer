package app.upaya.timer.session

import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*


class SessionHistoryRepositoryFakeTest {

    private val sessionLogRepository = SessionRepositoryFake()
    private val sessionHistoryRepository = SessionHistoryRepositoryFake(sessionLogRepository)

    @Test
    fun sessionLiveDataStatistics() = runBlocking {

        // GIVEN an empty SessionRepository
        var sessionAggregate = sessionHistoryRepository.getSessionAggregate()
        assert(sessionAggregate.avgLength == 0f)
        assert(sessionAggregate.sessionCount == 0)
        assert(sessionAggregate.totalLength == 0)

        // WHEN a session is added
        sessionLogRepository.storeSession(SessionLog(length = 2, endDate = Date(1000L)))

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionHistoryRepository.getSessionAggregate()
        assert(sessionAggregate.avgLength == 2f)
        assert(sessionAggregate.sessionCount == 1)
        assert(sessionAggregate.totalLength == 2)

        // AND WHEN another session is added
        sessionLogRepository.storeSession(SessionLog(length = 4, endDate = Date(2000L)))

        // THEN the corresponding LiveData is updated accordingly
        sessionAggregate = sessionHistoryRepository.getSessionAggregate()
        assert(sessionAggregate.avgLength == 3f)
        assert(sessionAggregate.sessionCount == 2)
        assert(sessionAggregate.totalLength == 6)
    }

}
