package com.example.android.databinding.basicsample.data

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import kotlin.math.round


enum class TimerStates {
    WAITING_FOR_START, RUNNING, WAITING_FOR_FEEDBACK
}


class MeditationTimer {

    val state = MutableLiveData(TimerStates.WAITING_FOR_START)
    val secondsLeft =  MutableLiveData(0)

    fun startCountdown() {

        secondsLeft.value = 3
        state.value = TimerStates.RUNNING

        val timerDuration: Long = (secondsLeft.value ?: 0).toLong() * 1000

        object: CountDownTimer(timerDuration, 1000) {

            override fun onTick(millisRemaining: Long) {
                secondsLeft.value = round(millisRemaining / 1000.0).toInt()
            }

            override fun onFinish() {
                secondsLeft.value = 0
                state.value = TimerStates.WAITING_FOR_FEEDBACK
            }

        }.start()

    }

    fun submitRating(rating: Float) {
        state.value = TimerStates.WAITING_FOR_START
    }

}
