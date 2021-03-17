package app.upaya.timer.session.repository.stats

import app.upaya.timer.session.repository.ISessionRepository
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*


class SessionStatsRepository(
    sessionRepository: ISessionRepository,
    aggregateLimit: Int = 10,
) : ISessionStatsRepository {

    override val sessionAggregate = sessionRepository.sessions.map { it.aggregate() }

    override val sessionAggregatesOfLastDays = sessionRepository.sessions.map { sessionLogs ->
        sessionLogs.groupBy {
            SimpleDateFormat(
                "y-M-d",
                Locale.getDefault()
            ).format(it.initDate)
        }
            .map { it.value.aggregate() }
            .sortedByDescending { aggregate -> aggregate.date }
            .takeLast(aggregateLimit)
    }

}
