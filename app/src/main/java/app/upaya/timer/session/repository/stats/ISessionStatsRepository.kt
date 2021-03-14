package app.upaya.timer.session.repository.stats

import kotlinx.coroutines.flow.Flow


interface ISessionStatsRepository {
    val sessionAggregate: Flow<SessionAggregate>
    val sessionAggregatesOfLastDays: Flow<List<SessionAggregate>>
}
