package app.upaya.timer.session.history

import androidx.lifecycle.LiveData


interface ISessionHistoryRepository {
    val sessionAggregateOfAll: LiveData<SessionAggregate>
    val sessionAggregateOfLastDays: LiveData<List<SessionAggregate>>
}
