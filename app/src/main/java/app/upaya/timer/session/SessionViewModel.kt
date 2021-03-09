package app.upaya.timer.session

import androidx.lifecycle.*


class SessionViewModel(
    sessionHandler: SessionHandler,
    initialSessionLength: Double
) : ViewModel() {

    /**
     * Session LiveData
     */

    val state: LiveData<SessionState> = SessionState.create(
        sessionHandler = sessionHandler,
        initialSessionLength = initialSessionLength
    )

    //private val _sessionLength = MutableLiveData(sessionTimer.getLength())
    //val sessionLength: LiveData<Double> = _sessionLength

    // Transformations
    val isRunning: LiveData<Boolean> = Transformations.map(state) { it is Running }
    val isIdle: LiveData<Boolean> = Transformations.map(state) { it is Idle }

}
