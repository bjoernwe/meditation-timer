package app.upaya.timer.timer

import android.content.Context
import androidx.lifecycle.*
import app.upaya.timer.sessions.Session
import app.upaya.timer.sessions.SessionRepository
import kotlinx.coroutines.*


class TimerViewModel(context: Context) : ViewModel() {

    private val timerRepository = TimerRepository(context)

    // Timer
    private val timer = Timer(
            sessionLength = timerRepository.loadSessionLength(),
            onStart = ::onTimerStart,
            onTick = ::onTimerTick,
            onFinish = ::onTimerFinish
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

    // Sessions
    private val sessionRepository = SessionRepository(context)

    // Timer events
    private fun onTimerStart() { _state.value = TimerStates.RUNNING }
    private fun onTimerTick(secondsRemaining: Double) { _secondsRemaining.value = secondsRemaining }

    private fun onTimerFinish(sessionLength: Double) {
        _secondsRemaining.value = 0.0
        _state.value = TimerStates.FINISHED
        storeFinishedSession(length = sessionLength.toInt())
        _state.value = TimerStates.WAITING_FOR_START
    }

    private fun onTimerStateChanged(newState: TimerStates) {
        when (newState) {
            TimerStates.FINISHED -> { }
            else -> { }
        }
    }

    private fun storeFinishedSession(length: Int) {
        val session = Session(length = length)
        MainScope().launch { sessionRepository.storeSession(session) }
    }

    override fun onCleared() {
        super.onCleared()
        state.removeObserver(stateObserver)
    }

    /**
     * Pass-through from Timer
     */

    fun startCountdown() {
        timer.startCountdown()
    }

    fun increaseSessionLength() {
        timer.increaseSessionLength()
        val newSessionLength = timer.getSessionLength()
        timerRepository.storeSessionLength(newSessionLength)
    }

    fun decreaseSessionLength() {
        timer.decreaseSessionLength()
        val newSessionLength = timer.getSessionLength()
        timerRepository.storeSessionLength(newSessionLength)
    }

}
