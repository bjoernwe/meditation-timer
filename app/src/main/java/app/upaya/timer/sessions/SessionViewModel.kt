package app.upaya.timer.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel


class SessionViewModel(sessionRepository: SessionRepository): ViewModel() {
    val sessionCount: LiveData<Int> = sessionRepository.sessionCount
    val sessionAvg: LiveData<Float> = sessionRepository.sessionAvg
}
