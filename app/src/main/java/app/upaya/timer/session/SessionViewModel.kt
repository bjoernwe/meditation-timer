package app.upaya.timer.session

import androidx.lifecycle.*
import app.upaya.timer.session_history.ISessionHistoryRepository


class SessionViewModel(sessionHandler: SessionHandler) : ViewModel() {

    /**
     * Session LiveData
     */

    val state: LiveData<SessionState> = SessionState.create(sessionHandler = sessionHandler)

    //private val _sessionLength = MutableLiveData(sessionTimer.getLength())
    //val sessionLength: LiveData<Double> = _sessionLength

    // Transformations
    val isRunning: LiveData<Boolean> = Transformations.map(state) { it is Running }
    val isIdle: LiveData<Boolean> = Transformations.map(state) { it is Idle }

    // Event Handling
    // We use observeForever() because we don't want to have any LivecycleOwner in the ViewModel.
    // The observer is removed in onCleared().
    private var stateObserver: Observer<SessionState> = Observer(::onTimerStateChanged)
    init { state.observeForever(stateObserver) }

    /**
     * Timer events / callbacks
     */

    private fun onTimerStateChanged(newState: SessionState) {
        when (newState) {
            is Idle -> {}
            is Running -> {}
            is Finished -> { storeFinishedSession() }
        }
    }

    private fun storeFinishedSession() {
        //sessionLength.value?.let {
        //    MainScope().launch { sessionHistoryRepository.storeSession(it) }
        //}
    }

    // ViewModel destructor
    override fun onCleared() {
        super.onCleared()
        state.removeObserver(stateObserver)
    }

}
