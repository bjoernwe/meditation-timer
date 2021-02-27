package app.upaya.timer.session

import androidx.lifecycle.*
import app.upaya.timer.session_history.ISessionHistoryRepository
import kotlinx.coroutines.*


class SessionViewModel(private val sessionRepository: ISessionRepository,
                       private val sessionHistoryRepository: ISessionHistoryRepository) : ViewModel() {

    // Session
    private val session = SessionHandler(
            sessionLength = sessionRepository.loadSessionLength(),
            onSessionLengthChanged = ::onSessionLengthChanged
    )

    /**
     * Timer LiveData
     */

    private val _secondsRemaining = MutableLiveData(0)
    val secondsRemaining: LiveData<Int> = _secondsRemaining

    private val _state: MutableLiveData<SessionState?> = MutableLiveData()
    val state: LiveData<SessionState> = Transformations.map(_state) { it }

    init { _state.postValue(Idle(session, _state, _secondsRemaining)) }

    private val _sessionLength = MutableLiveData(session.getLength())
    val sessionLength: LiveData<Double> = _sessionLength

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

    private fun onSessionLengthChanged(newSessionLength: Double) {
        _sessionLength.value = newSessionLength
        sessionRepository.storeSessionLength(newSessionLength)
    }

    private fun onTimerStateChanged(newState: SessionState) {
        when (newState) {
            is Idle -> {}
            is Running -> {}
            is Finished -> { storeFinishedSession() }
        }
    }

    private fun storeFinishedSession() {
        sessionLength.value?.let {
            MainScope().launch { sessionHistoryRepository.storeSession(it) }
        }
    }

    // ViewModel destructor
    override fun onCleared() {
        super.onCleared()
        state.removeObserver(stateObserver)
    }

}
