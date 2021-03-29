package app.upaya.timer.session.repository.stats

import app.upaya.timer.session.repository.ISessionRepository
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*


class SessionStatsRepository(
    sessionRepository: ISessionRepository,
    recentDaysLimit: Int = 10,
) : ISessionStatsRepository {

    override val experimentStats = sessionRepository.sessions.map { it.calcStats() }

    override val experimentStatsOfLastDays = sessionRepository.sessions.map { sessionLogs ->
        sessionLogs.groupBy {
            SimpleDateFormat(
                "y-M-d",
                Locale.getDefault()
            ).format(it.initDate)
        }
            .map { it.value.calcStats() }
            .sortedByDescending { stats -> stats.date }
            .takeLast(recentDaysLimit)
    }

}
