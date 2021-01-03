package app.upaya.timer.timer

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.math.round


class Timer private constructor(initialSessionLength: Double) {

    private val _sessionLength = MutableLiveData(initialSessionLength)
    private val _secondsLeft = MutableLiveData(0)
    private val _state = MutableLiveData(TimerStates.WAITING_FOR_START)

    val sessionLength: LiveData<Double> = _sessionLength
    val secondsLeft: LiveData<Int> = _secondsLeft
    val state: LiveData<TimerStates> = _state

    fun startCountdown() {

        if (_state.value != TimerStates.WAITING_FOR_START) return

        _secondsLeft.value = sessionLength.value?.toInt() ?: 10
        _state.value = TimerStates.RUNNING

        val timerDuration: Long = (secondsLeft.value ?: 0).toLong() * 1000

        object : CountDownTimer(timerDuration, 1000) {

            override fun onTick(millisRemaining: Long) {
                _secondsLeft.value = round(millisRemaining / 1000.0).toInt()
            }

            override fun onFinish() {
                _secondsLeft.value = 0
                _state.value = TimerStates.FINISHED  // trigger bell & feedback dialog
                _state.value = TimerStates.WAITING_FOR_START
            }

        }.start()

    }

    fun increaseSessionLength() {
        _sessionLength.value = _sessionLength.value?.times(1.1)
    }

    fun decreaseSessionLength() {
        val newSessionLength = _sessionLength.value?.times(0.8) ?: return
        if (newSessionLength >= 1.0) {
            _sessionLength.value = newSessionLength
        }
    }

    // Singleton
    companion object {

        @Volatile
        private var INSTANCE: Timer? = null

        fun getInstance(initialSessionLength: Double): Timer {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Timer(initialSessionLength)
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}
