package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel


class SessionViewModel(sessionRepository: ISessionRepository): ViewModel() {
    val sessionAggOfAll = sessionRepository.sessionAggregateOfAll
    val sessionAggOfLastDays = sessionRepository.sessionAggregateOfLastDays
    val sessions: LiveData<List<Session>> = sessionRepository.sessions
}
