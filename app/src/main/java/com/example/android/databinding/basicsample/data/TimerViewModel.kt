package com.example.android.databinding.basicsample.data

import android.app.Application
import android.content.Context
import android.widget.RatingBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.android.databinding.basicsample.ui.MainActivity


class TimerViewModel(application: Application) : AndroidViewModel(application), RatingBar.OnRatingBarChangeListener {

    private val timer = MeditationTimer(duration = 10.0)

    val state: LiveData<TimerStates> = timer.state
    val secondsLeft: LiveData<Int> = timer.secondsLeft

    fun startCountdown() {
        timer.startCountdown()
    }

    override fun onRatingChanged(ratingBar: RatingBar, rating: Float, fromUser: Boolean) {
        if (fromUser) {
            submitRating(rating, ratingBar.max)
            ratingBar.rating = 0F
        }
    }

    private fun submitRating(rating: Float, maxRating: Int) {
        val normalizedRating: Float = rating / maxRating.toFloat()
        timer.submitRating(normalizedRating)
    }

}
