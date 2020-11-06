package com.example.android.databinding.basicsample.ui

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.preference.PreferenceManager
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android.databinding.basicsample.R
import com.example.android.databinding.basicsample.data.TimerStates
import com.example.android.databinding.basicsample.data.TimerViewModel
import com.example.android.databinding.basicsample.data.TimerViewModelFactory
import com.example.android.databinding.basicsample.databinding.MainActivityBinding
import timber.log.Timber


class MainActivity : AppCompatActivity(), RatingBar.OnRatingBarChangeListener, MediaPlayer.OnErrorListener {

    private var prefs: SharedPreferences? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // SharedPreferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        // Obtain ViewModel from ViewModelProviders
        val timerViewModelFactory = TimerViewModelFactory(application)
        val timerViewModel = ViewModelProvider(this, timerViewModelFactory).get(TimerViewModel::class.java)

        // Obtain binding
        val binding: MainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.viewmodel = timerViewModel
        binding.lifecycleOwner = this

        // Register event callbacks
        binding.ratingBar.onRatingBarChangeListener = this
        timerViewModel.state.observe(this, { onTimerStateChanged(it) })
        timerViewModel.sessionLength.observe(this, { onSessionLengthChanged(it) })
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

    private fun onTimerStateChanged(newTimerState: TimerStates) {
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

    private fun onSessionLengthChanged(newSessionLength: Double) {
        prefs?.edit()?.putFloat(getString(R.string.pref_session_length), newSessionLength.toFloat())?.apply()
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
