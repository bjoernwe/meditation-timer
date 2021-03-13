package app.upaya.timer.session

import app.upaya.timer.session.history.ISessionHistoryRepository
import app.upaya.timer.session.history.SessionAggregate
import app.upaya.timer.session.history.aggregate
import java.text.SimpleDateFormat


class SessionHistoryRepositoryFake(
    private val sessionRepository: ISessionRepository
    ) : ISessionHistoryRepository {

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
