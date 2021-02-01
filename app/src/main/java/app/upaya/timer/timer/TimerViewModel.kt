package app.upaya.timer.timer

import androidx.lifecycle.*
import app.upaya.timer.sessions.ISessionRepository
import kotlinx.coroutines.*


class TimerViewModel(private val timerRepository: ITimerRepository,
                     private val sessionRepository: ISessionRepository) : ViewModel() {

    // Timer
    private val timer = Timer(
            sessionLength = timerRepository.loadSessionLength(),
            onSessionLengthChanged = ::onSessionLengthChanged
    )

    /**
     * Timer LiveData
     */

    private val _secondsRemaining = MutableLiveData(0)
    val secondsRemaining: LiveData<Int> = _secondsRemaining

    private val _state: MutableLiveData<TimerState?> = MutableLiveData()
    val state: LiveData<TimerState> = Transformations.map(_state) { it }

    init { _state.postValue(Idle(timer, _state, _secondsRemaining)) }

    private val _sessionLength = MutableLiveData(timer.getSessionLength())
    val sessionLength: LiveData<Double> = _sessionLength

    // Transformations
    val isRunning: LiveData<Boolean> = Transformations.map(state) { it is Running }
    val isIdle: LiveData<Boolean> = Transformations.map(state) { it is Idle }

    // Event Handling
    // We use observeForever() because we don't want to have any LivecycleOwner in the ViewModel.
    // The observer is removed in onCleared().
    private var stateObserver: Observer<TimerState> = Observer(::onTimerStateChanged)
    init { state.observeForever(stateObserver) }

    /**
     * Timer events / callbacks
     */

    private fun onSessionLengthChanged(newSessionLength: Double) {
        _sessionLength.value = newSessionLength
        timerRepository.storeSessionLength(newSessionLength)
    }

    private fun onTimerStateChanged(newState: TimerState) {
        when (newState) {
            is Idle -> {}
            is Running -> {}
            is Finished -> { storeFinishedSession() }
        }
    }

    private fun storeFinishedSession() {
        sessionLength.value?.let {
            MainScope().launch { sessionRepository.storeSession(it) }
        }
    }

    // ViewModel destructor
    override fun onCleared() {
        super.onCleared()
        state.removeObserver(stateObserver)
    }

}
