package app.upaya.timer.session.history

import androidx.lifecycle.ViewModel


class SessionHistoryViewModel(sessionHistoryRepository: ISessionHistoryRepository): ViewModel() {
    val sessionAggOfAll = sessionHistoryRepository.sessionAggregateOfAll
    val sessionAggOfLastDays = sessionHistoryRepository.sessionAggregateOfLastDays
}
