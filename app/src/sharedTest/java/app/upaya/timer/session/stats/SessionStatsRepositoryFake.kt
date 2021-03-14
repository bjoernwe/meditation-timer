package app.upaya.timer.session.stats

import app.upaya.timer.session.repository.ISessionRepository
import app.upaya.timer.session.repository.stats.ISessionStatsRepository
import app.upaya.timer.session.repository.stats.aggregate
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat


class SessionStatsRepositoryFake(
    sessionRepository: ISessionRepository,
    aggregateLimit: Int = 10,
    ) : ISessionStatsRepository {

    override val sessionAggregate = sessionRepository.sessions.map { it.aggregate() }

    override val sessionAggregatesOfLastDays = sessionRepository.sessions.map { sessionLogs ->
        sessionLogs.groupBy { SimpleDateFormat("y-M-d").format(it.endDate) }
        .map { it.value.aggregate() }
        .sortedByDescending { aggregate -> aggregate.date }
        .takeLast(aggregateLimit)
    }

}
