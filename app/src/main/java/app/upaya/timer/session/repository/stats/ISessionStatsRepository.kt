package app.upaya.timer.session.repository.stats


interface ISessionStatsRepository {
    suspend fun getSessionAggregate(): SessionAggregate
    suspend fun getSessionAggregateOfLastDays(limit: Int = 10): List<SessionAggregate>
}
