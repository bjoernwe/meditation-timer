package app.upaya.timer.session

import app.upaya.timer.session.repository.ISessionRepository
import app.upaya.timer.session.repository.stats.ISessionStatsRepository
import app.upaya.timer.session.repository.stats.SessionAggregate
import app.upaya.timer.session.repository.stats.aggregate
import java.text.SimpleDateFormat


class SessionStatsRepositoryFake(
    private val sessionRepository: ISessionRepository
    ) : ISessionStatsRepository {

    override suspend fun getSessionAggregate(): SessionAggregate {
        return sessionRepository.getSessions().aggregate()
    }

    override suspend fun getSessionAggregateOfLastDays(limit: Int): List<SessionAggregate> {
        return sessionRepository.getSessions()
            .groupBy { SimpleDateFormat("y-M-d").format(it.endDate) }
            .map { it.value.aggregate() }
            .sortedByDescending { aggregate -> aggregate.date }
            .takeLast(limit)
    }

}
