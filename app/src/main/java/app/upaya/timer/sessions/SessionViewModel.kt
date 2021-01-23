package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel


class SessionViewModel(sessionRepository: ISessionRepository): ViewModel() {
    val sessionAggOfLastDays = sessionRepository.sessionAggregateOfLastDays
    val sessionAvg: LiveData<Float> = sessionRepository.sessionAvg
    val sessionCount: LiveData<Int> = sessionRepository.sessionCount
    val sessionTotal: LiveData<Int> = sessionRepository.sessionTotal
    val sessions: LiveData<List<Session>> = sessionRepository.sessions
}
