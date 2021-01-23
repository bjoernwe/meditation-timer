package app.upaya.timer.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.R
import app.upaya.timer.timer.*
import app.upaya.timer.ui.composables.MainComposable
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber


class MainActivity : AppCompatActivity(), MediaPlayer.OnErrorListener {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var timerAnalyticsLogger: TimerAnalyticsLogger
    private lateinit var timerViewModel: TimerViewModel

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        timerViewModel = ViewModelProvider(this, TimerViewModelFactory(this)).get(TimerViewModel::class.java)

        // Emit Main Composable
        setContent {
            MainComposable(onClick = ::onCircleClicked)
        }

        // Register event callbacks
        timerViewModel.state.observe(this, { onTimerStateChanged(it) })
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

    private fun onCircleClicked() {
        if (timerViewModel.state.value == TimerStates.WAITING_FOR_START) {
            timerViewModel.startCountdown()
            vibrate(50, 100)
        }
    }

    private fun onTimerStateChanged(newTimerState: TimerStates) {
        when (newTimerState) {
            TimerStates.FINISHED -> {
                showSessionRatingDialog()
                playBell()
                timerAnalyticsLogger.logSessionFinished()
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

    private fun showSessionRatingDialog() {
        SessionRatingDialogFragment().apply { show(supportFragmentManager, tag) }
    }

    override fun onError(mediaPlayer: MediaPlayer?, what: Int, extra: Int): Boolean {
        val errorMessage = "MediaPlayer Error $what"
        Timber.e(errorMessage)
        FirebaseCrashlytics.getInstance().recordException(RuntimeException(errorMessage))
        return true
    }

}
