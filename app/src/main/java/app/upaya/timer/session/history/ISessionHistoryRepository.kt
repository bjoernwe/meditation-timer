package app.upaya.timer.session.history


interface ISessionHistoryRepository {
    suspend fun getSessionAggregate(): SessionAggregate
    suspend fun getSessionAggregateOfLastDays(limit: Int = 10): List<SessionAggregate>
}
