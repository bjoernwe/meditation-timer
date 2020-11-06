package com.example.android.meditation_timer.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel


class TimerViewModel(initialSessionLength: Double) : ViewModel() {

    private val timer = MeditationTimer(initialSessionLength)

    val sessionLength: LiveData<Double> = timer.sessionLength
    val secondsLeft: LiveData<Int> = timer.secondsLeft
    val state: LiveData<TimerStates> = timer.state

    fun startCountdown() {
        timer.startCountdown()
    }

    fun submitRating(rating: Float, maxRating: Int) {
        val normalizedRating: Float = rating / maxRating.toFloat()
        timer.submitRating(normalizedRating)
    }

}
