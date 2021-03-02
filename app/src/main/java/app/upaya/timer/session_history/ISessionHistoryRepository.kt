package app.upaya.timer.session_history

import androidx.lifecycle.LiveData


interface ISessionHistoryRepository {
    val sessionAggregateOfAll: LiveData<SessionAggregate>
    val sessionAggregateOfLastDays: LiveData<List<SessionAggregate>>
}
