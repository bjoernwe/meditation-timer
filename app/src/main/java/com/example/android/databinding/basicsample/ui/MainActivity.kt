package com.example.android.databinding.basicsample.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.databinding.basicsample.R
import com.example.android.databinding.basicsample.data.TimerStates
import com.example.android.databinding.basicsample.data.TimerViewModel
import com.example.android.databinding.basicsample.databinding.MainActivityBinding
import timber.log.Timber


class MainActivity : AppCompatActivity(), Observer<TimerStates>, RatingBar.OnRatingBarChangeListener, MediaPlayer.OnErrorListener {

    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Obtain ViewModel from ViewModelProviders
        val timerViewModel = ViewModelProvider(this).get(TimerViewModel::class.java)

        // Obtain binding
        val binding: MainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.viewmodel = timerViewModel
        binding.lifecycleOwner = this

        // Register event callbacks
        timerViewModel.state.observe(this, this)
        binding.ratingBar.onRatingBarChangeListener = this
        mediaPlayer?.setOnErrorListener(this)

    }

    override fun onStart() {
        super.onStart()
        applicationContext.resources.openRawResourceFd(R.raw.bell_347378).use {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(it)
            mediaPlayer?.prepareAsync()
        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onChanged(newTimerState: TimerStates?) {
        when (newTimerState) {
            TimerStates.FINISHED -> { playBell() }
            else -> {}
        }
    }

    private fun playBell() {
        mediaPlayer?.seekTo(0)
        mediaPlayer?.start()
        getSystemService(Vibrator::class.java).vibrate(
                VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
        )
    }

    override fun onRatingChanged(ratingBar: RatingBar, rating: Float, fromUser: Boolean) {
        if (fromUser) {
            val timerViewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
            timerViewModel.submitRating(rating, ratingBar.max)
            ratingBar.rating = 0F
        }
    }

    override fun onError(mediaPlayer: MediaPlayer?, what: Int, extra: Int): Boolean {
        Timber.e("MediaPlayer Error $what")
        return false
    }

}
