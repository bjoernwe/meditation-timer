package app.upaya.timer.session.repository.stats

import app.upaya.timer.session.repository.room.SessionLogDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SessionStatsRepository(sessionLogDatabase: SessionLogDatabase) : ISessionStatsRepository {

    private val sessionStatsDao = sessionLogDatabase.sessionStatsDao

    override suspend fun getSessionAggregate(): SessionAggregate {
        return sessionStatsDao.getAggregateOfAll()
    }

    override suspend fun getSessionAggregateOfLastDays(limit: Int): List<SessionAggregate> {
        return sessionStatsDao.getAggregateOfLastDays(limit = limit)
    }

}
