package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel


class SessionViewModel(sessionRepository: ISessionRepository): ViewModel() {
    private val sessionRepository = SessionRepository(application)
    val avg: LiveData<Float?> = sessionRepository.avg
    val avgOfLastDays = sessionRepository.avgOfLastDays
    val sessionCount: LiveData<Int> = sessionRepository.sessionCount
    val sessionAvg: LiveData<Float> = sessionRepository.sessionAvg
}
