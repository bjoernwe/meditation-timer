package app.upaya.timer.timer

import android.app.Application
import androidx.lifecycle.*
import app.upaya.timer.sessions.Session
import app.upaya.timer.sessions.SessionRepository
import app.upaya.timer.ui.fromSecsToTimeString
import kotlinx.coroutines.*


class TimerViewModel(application: Application, initialSessionLength: Double) : AndroidViewModel(application) {

    // Timer
    val timer = Timer(initialSessionLength)

    // Transformations
    val isRunning: LiveData<Boolean> = Transformations.map(timer.state) { it == TimerStates.RUNNING }
    val secondsLeftString: LiveData<String> = Transformations.map(timer.secondsLeft) { fromSecsToTimeString(it) }

    // Event Handling
    private var stateObserver: Observer<TimerStates> = Observer { onTimerStateChanged(it) }
    init { timer.state.observeForever(stateObserver) }

    // Sessions
    private val sessionRepository = SessionRepository(application)

    private fun onTimerStateChanged(newState: TimerStates) {
        when (newState) {
            TimerStates.FINISHED -> {
                logFinishedSession()
            } else -> { }
        }
    }

    private fun logFinishedSession() {
        timer.sessionLength.value?.let {
            val session = Session(length = it.toInt())
            MainScope().launch { sessionRepository.storeSession(session) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer.state.removeObserver(stateObserver)
    }

}
