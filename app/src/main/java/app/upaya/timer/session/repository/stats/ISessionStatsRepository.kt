package app.upaya.timer.session.repository.stats

import app.upaya.timer.experiments.repository.stats.ExperimentStats
import kotlinx.coroutines.flow.Flow


interface ISessionStatsRepository {
    val experimentStats: Flow<ExperimentStats>
    val experimentStatsOfLastDays: Flow<List<ExperimentStats>>
}
