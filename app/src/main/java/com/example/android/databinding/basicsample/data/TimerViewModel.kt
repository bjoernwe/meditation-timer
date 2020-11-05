package com.example.android.databinding.basicsample.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel


class TimerViewModel : ViewModel() {

    private val timer = MeditationTimer(duration = 10.0)

    val state: LiveData<TimerStates> = timer.state
    val secondsLeft: LiveData<Int> = timer.secondsLeft

    fun startCountdown() {
        timer.startCountdown()
    }

    fun submitRating(rating: Float, maxRating: Int) {
        val normalizedRating: Float = rating / maxRating.toFloat()
        timer.submitRating(normalizedRating)
    }

}
