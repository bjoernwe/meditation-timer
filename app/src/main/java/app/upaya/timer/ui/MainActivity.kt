package app.upaya.timer.ui

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.R
import app.upaya.timer.timer.TimerAnalyticsLogger
import app.upaya.timer.timer.TimerStates
import app.upaya.timer.timer.TimerViewModel
import app.upaya.timer.timer.TimerViewModelFactory
import timber.log.Timber


class MainActivity : AppCompatActivity(), MediaPlayer.OnErrorListener {

    private var mediaPlayer: MediaPlayer? = null
    private var timerAnalyticsLogger: TimerAnalyticsLogger? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Obtain ViewModel from ViewModelProviders
        val timerViewModelFactory = TimerViewModelFactory(application)
        val timerViewModel = ViewModelProvider(this, timerViewModelFactory).get(TimerViewModel::class.java)

        // Emit Main Composable
        setContent {
            MainComposable(
                    isRunning = timerViewModel.isRunning.observeAsState(initial = false),
                    secondsLeft = timerViewModel.secondsLeftString.observeAsState(initial = ""),
                    onClick = { onCircleClicked(timerViewModel) }
            )
        }

        // Register event callbacks
        timerViewModel.timer.state.observe(this, { onTimerStateChanged(it) })
        timerViewModel.timer.sessionLength.observe(this, { onSessionLengthChanged(it) })
        mediaPlayer?.setOnErrorListener(this)

        // Firebase Analytics
        timerAnalyticsLogger = TimerAnalyticsLogger(this)
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

    private fun onCircleClicked(timerViewModel: TimerViewModel) {
        if (timerViewModel.timer.state.value == TimerStates.WAITING_FOR_START) {
            timerViewModel.timer.startCountdown()
            vibrate(50, 100)
        }
    }

    private fun onTimerStateChanged(newTimerState: TimerStates) {
        when (newTimerState) {
            TimerStates.FINISHED -> {
                showSessionRatingDialog()
                playBell()
                timerAnalyticsLogger?.logSessionFinished()
            }
            else -> { }
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
        val prefs = application.getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE)
        prefs.edit { putFloat(getString(R.string.pref_session_length), newSessionLength) }
    }

    private fun showSessionRatingDialog() {
        SessionRatingDialogFragment().apply { show(supportFragmentManager, tag) }
    }

    override fun onError(mediaPlayer: MediaPlayer?, what: Int, extra: Int): Boolean {
        Timber.e("MediaPlayer Error $what")
        return false
    }

}
