package app.upaya.timer.experiments.repositories.stats

import app.upaya.timer.experiments.repositories.logs.IExperimentLogRepository
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*


class ExperimentStatsRepository(
    experimentLogRepository: IExperimentLogRepository,
    recentDaysLimit: Int = 10,
) : IExperimentStatsRepository {

    override val experimentStats = experimentLogRepository.experiments.map { it.calcStats() }

    override val experimentStatsOfLastDays = experimentLogRepository.experiments.map { sessionLogs ->
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
