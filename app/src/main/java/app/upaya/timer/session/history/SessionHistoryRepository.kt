package app.upaya.timer.session.history

import app.upaya.timer.session.room.SessionLogDatabase


class SessionHistoryRepository(sessionLogDatabase: SessionLogDatabase) : ISessionHistoryRepository {

    private val sessionHistoryDao = sessionLogDatabase.sessionHistoryDao

    override suspend fun getSessionAggregate(): SessionAggregate {
        return sessionHistoryDao.getAggregateOfAll()
    }

    override suspend fun getSessionAggregateOfLastDays(limit: Int): List<SessionAggregate> {
        return sessionHistoryDao.getAggregateOfLastDays(limit = limit)
    }

}
