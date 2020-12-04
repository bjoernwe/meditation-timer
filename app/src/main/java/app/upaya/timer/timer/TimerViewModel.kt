package app.upaya.timer.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel


class TimerViewModel(initialSessionLength: Double) : ViewModel() {

    private val timer = MeditationTimer(initialSessionLength)

    val sessionLength: LiveData<Double> = timer.sessionLength
    val secondsLeft: LiveData<Int> = timer.secondsLeft
    val state: LiveData<TimerStates> = timer.state

    val isRunning: LiveData<Boolean> = Transformations.map(state) { it == TimerStates.RUNNING }
    val secondsLeftString: LiveData<String> = Transformations.map(secondsLeft) { fromSecsToTimeString(it) }

    fun startCountdown() {
        if (state.value == TimerStates.WAITING_FOR_START) {
            timer.startCountdown()
        }
    }

    fun submitRating(rating: Float) {
        timer.submitRating(rating)
    }

    private fun fromSecsToTimeString(seconds: Int) : String {
        return "%d:%02d:%02d".format(seconds/3600, seconds/60, seconds%60)
    }

}
