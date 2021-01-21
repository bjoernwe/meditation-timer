package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel


class SessionViewModel(sessionRepository: ISessionRepository): ViewModel() {
    val sessionAvg: LiveData<Float> = sessionRepository.sessionAvg
    //val avgOfLastDays: LiveData<List<SessionAvgResult>> = sessionRepository.sessionAvgOfLastDays
    val sessionCount: LiveData<Int> = sessionRepository.sessionCount
    val sessionTotal: LiveData<Int> = sessionRepository.sessionTotal
    val sessions: LiveData<List<Session>> = sessionRepository.sessions
}
