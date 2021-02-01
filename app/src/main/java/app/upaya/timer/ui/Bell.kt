package app.upaya.timer.ui

import android.content.Context
import android.media.MediaPlayer
import android.os.VibrationEffect
import android.os.Vibrator
import app.upaya.timer.R
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber


class Bell(private val context: Context, hasPlayed: Boolean = false) : MediaPlayer.OnErrorListener{

    companion object {
        const val HAS_PLAYED_KEY = "bellHasPlayed"
    }

    private var mediaPlayer: MediaPlayer? = null
    var hasPlayed = hasPlayed
        private set

    /**
     * Play sound once. To play it again, call [reset] first.
     */
    fun play() {
        if (!hasPlayed) {
            mediaPlayer?.seekTo(0)
            mediaPlayer?.start()
            hasPlayed = true
            vibrate(1000)
        }
    }

    fun vibrate(milliseconds: Long, amplitude: Int = VibrationEffect.DEFAULT_AMPLITUDE) {
        context.getSystemService(Vibrator::class.java).vibrate(
                VibrationEffect.createOneShot(milliseconds, amplitude)
        )
    }

    /**
     * Reset the bell, i.e., let it play again when calling [play].
     */
    fun reset() {
        hasPlayed = false
    }

    /**
     * Initialize the media player, i.e., load the sound resource into memory. Should be called
     * from Activity.onStart().
     */
    fun init() {
        context.resources.openRawResourceFd(R.raw.bell_347378).use {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(it)
            mediaPlayer?.prepareAsync()
        }
        mediaPlayer?.setOnErrorListener(this)
    }

    /**
     * Release the media player, i.e., free the memory occupied by the sound resource. Should be
     * called from Activity.onStop().
     */
    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onError(mediaPlayer: MediaPlayer?, what: Int, extra: Int): Boolean {
        val errorMessage = "MediaPlayer Error $what"
        Timber.e(errorMessage)
        FirebaseCrashlytics.getInstance().recordException(RuntimeException(errorMessage))
        return true
    }

}
