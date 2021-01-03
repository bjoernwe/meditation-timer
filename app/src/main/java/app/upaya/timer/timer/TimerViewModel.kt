package app.upaya.timer.timer

import android.app.Application
import androidx.lifecycle.*
import app.upaya.timer.data.Session
import app.upaya.timer.data.SessionDatabase
import app.upaya.timer.ui.fromSecsToTimeString
import kotlinx.coroutines.*


class TimerViewModel(application: Application, initialSessionLength: Double) : AndroidViewModel(application) {

    // Timer
    val timer = MeditationTimer(initialSessionLength)

    // Transformations
    val isRunning: LiveData<Boolean> = Transformations.map(timer.state) { it == TimerStates.RUNNING }
    val secondsLeftString: LiveData<String> = Transformations.map(timer.secondsLeft) { fromSecsToTimeString(it) }

    // Sessions
    private val sessionDao = SessionDatabase.getInstance(application).sessionDao
    val sessionCount: LiveData<Int> = sessionDao.getSessionCount()
    val sessionAvg: LiveData<Float> = sessionDao.getSessionAvg()

    // Coroutines
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Event Handling
    private var stateObserver: Observer<TimerStates> = Observer { onTimerStateChanged(it) }
    init { timer.state.observeForever(stateObserver) }

    private fun onTimerStateChanged(newState: TimerStates) {
        when (newState) {
            TimerStates.FINISHED -> {
                logSessionFinished()
            } else -> { }
        }
    }

    private fun logSessionFinished() {
        uiScope.launch {
            storeSession()
        }
    }

    private suspend fun storeSession() {
        withContext(Dispatchers.IO) {
            timer.sessionLength.value?.let { sessionDao.insert(Session(length = it.toInt())) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        timer.state.removeObserver(stateObserver)
    }

}
