package app.upaya.timer.timer

import android.app.Application
import androidx.lifecycle.*
import app.upaya.timer.data.Session
import app.upaya.timer.data.SessionDatabase
import kotlinx.coroutines.*


class TimerViewModel(application: Application, initialSessionLength: Double) : AndroidViewModel(application) {

    // Timer
    val timer = MeditationTimer(initialSessionLength)

    // Transformations
    val isRunning: LiveData<Boolean> = Transformations.map(timer.state) { it == TimerStates.RUNNING }
    val secondsLeftString: LiveData<String> = Transformations.map(timer.secondsLeft) { fromSecsToTimeString(it) }

    // Room Database
    private val db = SessionDatabase.getInstance(this.getApplication())
    private val sessionDao = db.sessionDao

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

    private fun fromSecsToTimeString(seconds: Int) : String {
        return "%d:%02d:%02d".format(seconds/3600, seconds/60, seconds%60)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        timer.state.removeObserver(stateObserver)
    }

}
