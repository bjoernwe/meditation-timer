package app.upaya.timer.sessions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class SessionViewModel(application: Application): AndroidViewModel(application) {

    private val sessionRepository = SessionRepository(application)
    val sessionCount: LiveData<Int> = sessionRepository.sessionCount
    val sessionAvg: LiveData<Float> = sessionRepository.sessionAvg

}
