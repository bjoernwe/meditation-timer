package app.upaya.timer.ui

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
import app.upaya.timer.R
import app.upaya.timer.timer.TimerAnalyticsLogger
import app.upaya.timer.databinding.MainActivityBinding
import app.upaya.timer.timer.TimerStates
import app.upaya.timer.timer.TimerViewModel
import app.upaya.timer.timer.TimerViewModelFactory
import timber.log.Timber


class MainActivity : AppCompatActivity(), MediaPlayer.OnErrorListener {

    private var prefs: SharedPreferences? = null
    private var mediaPlayer: MediaPlayer? = null

    private var timerViewModel: TimerViewModel? = null
    private var timerAnalyticsLogger: TimerAnalyticsLogger? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // SharedPreferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        // Obtain ViewModel from ViewModelProviders
        val timerViewModelFactory = TimerViewModelFactory(application)
        timerViewModel = ViewModelProvider(this, timerViewModelFactory).get(TimerViewModel::class.java)

        // Firebase Analytics
        timerAnalyticsLogger = TimerAnalyticsLogger(this, timerViewModel)

        // Obtain binding
        val binding: MainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.viewmodel = timerViewModel
        binding.lifecycleOwner = this

        // Register event callbacks
        timerViewModel?.state?.observe(this, { onTimerStateChanged(it) })
        timerViewModel?.sessionLength?.observe(this, { onSessionLengthChanged(it) })
        binding.circleView.setOnClickListener { onCircleClicked() }
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

    private fun onCircleClicked() {
        timerViewModel?.startCountdown()
        vibrate(50, 100)
    }

    private fun onTimerStateChanged(newTimerState: TimerStates) {
        when (newTimerState) {
            TimerStates.FINISHED -> {
                playBell()
                showSessionRatingDialog()
                timerAnalyticsLogger?.logSessionFinished()
            }
            else -> {}
        }
    }

    private fun playBell() {
        mediaPlayer?.seekTo(0)
        mediaPlayer?.start()
        vibrate(1000)
    }

    private fun vibrate(milliseconds: Long, amplitude: Int = VibrationEffect.DEFAULT_AMPLITUDE) {
        getSystemService(Vibrator::class.java).vibrate(
                VibrationEffect.createOneShot(milliseconds, amplitude)
        )
    }

    private fun onSessionLengthChanged(newSessionLength: Double) {
        saveSessionLength(newSessionLength.toFloat())
    }

    private fun saveSessionLength(newSessionLength: Float) {
        prefs?.edit()?.putFloat(getString(R.string.pref_session_length), newSessionLength)?.apply()
    }

    private fun showSessionRatingDialog() {
        SessionRatingDialogFragment().apply { show(supportFragmentManager, tag) }
    }

    override fun onError(mediaPlayer: MediaPlayer?, what: Int, extra: Int): Boolean {
        Timber.e("MediaPlayer Error $what")
        return false
    }

}
