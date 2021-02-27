package app.upaya.timer.session_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import app.upaya.timer.session_history.room_entries.SessionEntry


class SessionHistoryViewModel(sessionHistoryRepository: ISessionHistoryRepository): ViewModel() {
    val sessionAggOfAll = sessionHistoryRepository.sessionAggregateOfAll
    val sessionAggOfLastDays = sessionHistoryRepository.sessionAggregateOfLastDays
    val sessions: LiveData<List<SessionEntry>> = sessionHistoryRepository.sessions
}
