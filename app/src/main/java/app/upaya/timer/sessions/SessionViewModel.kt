package app.upaya.timer.sessions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class SessionViewModel(application: Application): AndroidViewModel(application) {

    private val sessionRepository = SessionRepository(application)
    val avg: LiveData<Float?> = sessionRepository.avg
    val avgOfLastDays = sessionRepository.avgOfLastDays
    val count: LiveData<Int> = sessionRepository.count
    val sessions: LiveData<List<Session>> = sessionRepository.sessions

}
