package app.upaya.timer.session.repository.stats

import kotlinx.coroutines.flow.Flow


interface ISessionStatsRepository {
    val sessionStats: Flow<SessionStats>
    val sessionStatsOfLastDays: Flow<List<SessionStats>>
}
