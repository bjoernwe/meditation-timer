package app.upaya.timer.session.stats

import app.upaya.timer.session.room.SessionLogDatabase


class SessionStatsRepository(sessionLogDatabase: SessionLogDatabase) : ISessionStatsRepository {

    private val sessionStatsDao = sessionLogDatabase.sessionStatsDao

    override suspend fun getSessionAggregate(): SessionAggregate {
        return sessionStatsDao.getAggregateOfAll()
    }

    override suspend fun getSessionAggregateOfLastDays(limit: Int): List<SessionAggregate> {
        return sessionStatsDao.getAggregateOfLastDays(limit = limit)
    }

}
