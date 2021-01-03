package app.upaya.timer.timer

import android.app.Application
import androidx.lifecycle.*
import app.upaya.timer.sessions.Session
import app.upaya.timer.sessions.SessionRepository
import app.upaya.timer.ui.fromSecsToTimeString
import kotlinx.coroutines.*


class TimerViewModel(application: Application, initialSessionLength: Double) : AndroidViewModel(application) {

    // Timer
    val timer = Timer(
            sessionLength = initialSessionLength,
            onStart = ::onTimerStart,
            onTick = ::onTimerTick,
            onFinish = ::onTimerFinish
    )

    // Timer LiveData
    private val _secondsRemaining = MutableLiveData(0.0)
    private val _sessionLength = MutableLiveData(timer.getSessionLength())
    private val _state = MutableLiveData(TimerStates.WAITING_FOR_START)
    private val secondsRemaining: LiveData<Double> = _secondsRemaining
    val sessionLength: LiveData<Double> = _sessionLength
    val state: LiveData<TimerStates> = _state

    // Transformations
    val isRunning: LiveData<Boolean> = Transformations.map(state) { it == TimerStates.RUNNING }
    val secondsRemainingString: LiveData<String> = Transformations.map(secondsRemaining) {
        fromSecsToTimeString(it.toInt())
    }

    // Event Handling
    private var stateObserver: Observer<TimerStates> = Observer { onTimerStateChanged(it) }
    init { state.observeForever(stateObserver) }

    // Sessions
    private val sessionRepository = SessionRepository(application)

    // Timer events
    private fun onTimerStart() { _state.value = TimerStates.RUNNING }
    private fun onTimerTick(secondsRemaining: Double) { _secondsRemaining.value = secondsRemaining }

    private fun onTimerFinish() {
        _secondsRemaining.value = 0.0
        _state.value = TimerStates.FINISHED
        _state.value = TimerStates.WAITING_FOR_START
    }

    private fun onTimerStateChanged(newState: TimerStates) {
        when (newState) {
            TimerStates.FINISHED -> {
                logFinishedSession()
            } else -> { }
        }
    }

    private fun logFinishedSession() {
        sessionLength.value?.let {
            val session = Session(length = it.toInt())
            MainScope().launch { sessionRepository.storeSession(session) }
        }
    }

    fun startCountdown() {
        timer.startCountdown()
    }

    override fun onCleared() {
        super.onCleared()
        state.removeObserver(stateObserver)
    }

}
