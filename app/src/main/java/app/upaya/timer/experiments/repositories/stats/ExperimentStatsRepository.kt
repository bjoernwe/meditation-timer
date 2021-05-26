package app.upaya.timer.experiments.repositories.stats

import app.upaya.timer.experiments.repositories.logs.IExperimentLogRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import java.text.SimpleDateFormat
import java.util.*


class ExperimentStatsRepository(
    experimentLogRepository: IExperimentLogRepository,
    recentDaysLimit: Int = 10,
) : IExperimentStatsRepository {

    override val experimentStats = experimentLogRepository.experiments.map { it.calcStats() }

    @ExperimentalCoroutinesApi
    override val experimentStatsOfLastDays: Flow<List<ExperimentStats>> = experimentLogRepository.experiments.mapLatest { experimentLogs ->
        experimentLogs.groupBy {
            SimpleDateFormat("y-M-d", Locale.getDefault()).format(it.initDate)
        }
            .map { it.value.calcStats() }
            .sortedBy { stats -> stats.date }
            .takeLast(recentDaysLimit)
    }

}
