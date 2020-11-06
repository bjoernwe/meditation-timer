package com.example.android.databinding.basicsample.data

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.math.round


enum class TimerStates {
    WAITING_FOR_START, RUNNING, FINISHED, WAITING_FOR_FEEDBACK
}


class MeditationTimer(private var duration: Double = 10.0) {

    private val _state = MutableLiveData(TimerStates.WAITING_FOR_START)
    private val _secondsLeft =  MutableLiveData(0)

    val state: LiveData<TimerStates> = _state
    val secondsLeft: LiveData<Int> = _secondsLeft

    fun startCountdown() {

        _secondsLeft.value = duration.toInt()
        _state.value = TimerStates.RUNNING

        val timerDuration: Long = (secondsLeft.value ?: 0).toLong() * 1000

        object: CountDownTimer(timerDuration, 1000) {

            override fun onTick(millisRemaining: Long) {
                _secondsLeft.value = round(millisRemaining / 1000.0).toInt()
            }

            override fun onFinish() {
                _secondsLeft.value = 0
                _state.value = TimerStates.FINISHED  // trigger bell
                _state.value = TimerStates.WAITING_FOR_FEEDBACK
            }

        }.start()

    }

    fun submitRating(rating: Float) {
        _state.value = TimerStates.WAITING_FOR_START
        duration *= if (rating >= .5) 1.1 else 0.8
    }

}
