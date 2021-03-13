package app.upaya.timer.session.repository.stats

import app.upaya.timer.session.repository.room.SessionStatsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SessionStatsRepository(private val sessionStatsDao: SessionStatsDao) : ISessionStatsRepository {

    override suspend fun getSessionAggregate(): SessionAggregate {
        return withContext(Dispatchers.IO) {
            return@withContext sessionStatsDao.getAggregateOfAll()
        }
    }

    override suspend fun getSessionAggregateOfLastDays(limit: Int): List<SessionAggregate> {
        return withContext(Dispatchers.IO) {
            return@withContext sessionStatsDao.getAggregateOfLastDays(limit = limit)
        }
    }

}
