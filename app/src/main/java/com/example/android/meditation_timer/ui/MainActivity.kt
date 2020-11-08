package com.example.android.meditation_timer.ui

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.preference.PreferenceManager
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android.meditation_timer.R
import com.example.android.meditation_timer.timer.TimerStates
import com.example.android.meditation_timer.timer.TimerViewModel
import com.example.android.meditation_timer.timer.TimerViewModelFactory
import com.example.android.meditation_timer.databinding.MainActivityBinding
import timber.log.Timber


class MainActivity : AppCompatActivity(), MediaPlayer.OnErrorListener {

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
        timerViewModel.state.observe(this, { onTimerStateChanged(it) })
        timerViewModel.sessionLength.observe(this, { onSessionLengthChanged(it) })
        mediaPlayer?.setOnErrorListener(this)
    }

    override fun onStart() {
        super.onStart()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        applicationContext.resources.openRawResourceFd(R.raw.bell_347378).use {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(it)
            mediaPlayer?.prepareAsync()
        }
    }

    override fun onStop() {
        super.onStop()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun onTimerStateChanged(newTimerState: TimerStates) {
        when (newTimerState) {
            TimerStates.FINISHED -> {
                playBell()
                showSessionRatingDialog()
            }
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

    private fun showSessionRatingDialog() {
        SessionRatingDialogFragment().apply { show(supportFragmentManager, tag) }
    }

    override fun onError(mediaPlayer: MediaPlayer?, what: Int, extra: Int): Boolean {
        Timber.e("MediaPlayer Error $what")
        return false
    }

}
