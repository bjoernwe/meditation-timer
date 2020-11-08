package com.example.android.meditation_timer.timer

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.math.round


enum class TimerStates {
    WAITING_FOR_START, RUNNING, FINISHED
}


class MeditationTimer(initialSessionLength: Double = 10.0) {

    private val _sessionLength = MutableLiveData(initialSessionLength)
    private val _secondsLeft =  MutableLiveData(0)
    private val _state = MutableLiveData(TimerStates.WAITING_FOR_START)

    val sessionLength: LiveData<Double> = _sessionLength
    val secondsLeft: LiveData<Int> = _secondsLeft
    val state: LiveData<TimerStates> = _state

    fun startCountdown() {

        _secondsLeft.value = sessionLength.value?.toInt() ?: 10
        _state.value = TimerStates.RUNNING

        val timerDuration: Long = (secondsLeft.value ?: 0).toLong() * 1000

        object: CountDownTimer(timerDuration, 1000) {

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

    fun submitRating(rating: Float) {
        if (rating >= .5) {
            _sessionLength.value?.times(1.1)
        } else {
            _sessionLength.value?.times(0.8)
        }
    }

}
