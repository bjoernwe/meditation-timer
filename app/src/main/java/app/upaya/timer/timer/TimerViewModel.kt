package app.upaya.timer.timer

import androidx.lifecycle.*
import app.upaya.timer.sessions.ISessionRepository
import app.upaya.timer.sessions.Session
import kotlinx.coroutines.*


class TimerViewModel(private val timerRepository: ITimerRepository,
                     private val sessionRepository: ISessionRepository) : ViewModel() {

    // Timer
    private val timer = Timer(
            sessionLength = timerRepository.loadSessionLength(),
            onStart = ::onTimerStart,
            onTick = ::onTimerTick,
            onFinish = ::onTimerFinish,
            onSessionLengthChanged = ::onSessionLengthChanged
    )

    // Timer LiveData
    private val _secondsRemaining = MutableLiveData(0.0)
    private val _sessionLength = MutableLiveData(timer.getSessionLength())
    private val _state = MutableLiveData(TimerStates.WAITING_FOR_START)
    val secondsRemaining: LiveData<Double> = _secondsRemaining
    val sessionLength: LiveData<Double> = _sessionLength
    val state: LiveData<TimerStates> = _state

    // Transformations
    val isRunning: LiveData<Boolean> = Transformations.map(state) { it == TimerStates.RUNNING }

    // Event Handling
    // We use observeForever() because we don't want to have any LivecycleOwner in the ViewModel.
    // The observer is removed in onCleared().
    private var stateObserver: Observer<TimerStates> = Observer(::onTimerStateChanged)
    init { state.observeForever(stateObserver) }

    /**
     * Timer events / callbacks
     */

    private fun onTimerStart() { _state.value = TimerStates.RUNNING }

    private fun onTimerTick(secondsRemaining: Double) { _secondsRemaining.postValue(secondsRemaining) }

    private fun onTimerFinish() {
        _secondsRemaining.postValue(0.0)
        _state.postValue(TimerStates.FINISHED)
    }

    private fun onSessionLengthChanged(newSessionLength: Double) {
        _sessionLength.value = newSessionLength
        timerRepository.storeSessionLength(newSessionLength)
    }

    /**
     * Handle changes in TimerState
     */

    private fun onTimerStateChanged(newState: TimerStates) {
        when (newState) {
            TimerStates.FINISHED -> {
                _state.postValue(TimerStates.WAITING_FOR_START)  // FINISHED only serves as event
                storeFinishedSession()
            } else -> { }
        }
    }

    private fun storeFinishedSession() {
        sessionLength.value?.let {
            val session = Session(length = it.toInt())
            MainScope().launch { sessionRepository.storeSession(session) }
        }
    }

    // Pass-through to Timer
    fun startCountdown() { timer.startCountdown() }
    fun increaseSessionLength() { timer.increaseSessionLength() }
    fun decreaseSessionLength() { timer.decreaseSessionLength() }

    // ViewModel destructor
    override fun onCleared() {
        super.onCleared()
        state.removeObserver(stateObserver)
    }

}
