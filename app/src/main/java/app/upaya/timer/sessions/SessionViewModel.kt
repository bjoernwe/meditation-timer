package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel


class SessionViewModel(sessionRepository: ISessionRepository): ViewModel() {
    val sessionCount: LiveData<Int> = sessionRepository.sessionCount
    val sessionAvg: LiveData<Float> = sessionRepository.sessionAvg
}
