package app.upaya.timer.session.repository.stats

import app.upaya.timer.session.repository.room.SessionStatsDao


class SessionStatsRepository(private val sessionStatsDao: SessionStatsDao) : ISessionStatsRepository {
    override val sessionAggregate = sessionStatsDao.getAggregate()
    override val sessionAggregatesOfLastDays = sessionStatsDao.getAggregatesOfLastDays()
}
