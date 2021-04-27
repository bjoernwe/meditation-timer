package app.upaya.timer.experiments.repositories.stats

import kotlinx.coroutines.flow.Flow


interface IExperimentStatsRepository {
    val experimentStats: Flow<ExperimentStats>
    val experimentStatsOfLastDays: Flow<List<ExperimentStats>>
}
