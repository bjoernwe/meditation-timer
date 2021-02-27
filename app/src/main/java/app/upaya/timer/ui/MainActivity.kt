package app.upaya.timer.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.session.*
import app.upaya.timer.ui.composables.MainLayout


class MainActivity : AppCompatActivity() {

    private lateinit var bell: Bell
    private lateinit var sessionViewModel: SessionViewModel

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        sessionViewModel = ViewModelProvider(this, SessionViewModelFactory(this)).get(SessionViewModel::class.java)

        bell = Bell(
                context = applicationContext,
                hasPlayed = (savedInstanceState ?: Bundle()).getBoolean(Bell.HAS_PLAYED_KEY)
        )

        // Emit Main Composable
        setContent {
            MainLayout(onClick = ::onCircleClicked)
        }

        // Register event callbacks
        sessionViewModel.state.observe(this, { onTimerStateChanged(it) })
    }

    override fun onStart() {
        super.onStart()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        bell.init()
    }

    override fun onStop() {
        super.onStop()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        bell.release()
    }

    private fun onCircleClicked() {
        if (sessionViewModel.state.value is Idle) {
            (sessionViewModel.state.value as? Idle)?.startSession()
            bell.vibrate(50, 100)
        }
    }

    private fun onTimerStateChanged(newSessionState: SessionState) {
        when (newSessionState) {
            is Idle -> {}
            is Running -> { bell.reset() }
            is Finished -> { bell.play() }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(Bell.HAS_PLAYED_KEY, bell.hasPlayed)
    }

}
