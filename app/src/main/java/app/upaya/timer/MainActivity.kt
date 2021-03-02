package app.upaya.timer

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.session.*
import app.upaya.timer.session_length.SessionLengthRepository
import app.upaya.timer.ui.Bell
import app.upaya.timer.ui.composables.MainLayout


class MainActivity : AppCompatActivity() {

    private lateinit var bell: Bell
    private lateinit var sessionViewModel: SessionViewModel
    private lateinit var sessionLengthRepository: SessionLengthRepository

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // late init
        val sessionViewModelFactory = SessionViewModelFactory(this)
        sessionViewModel = ViewModelProvider(this, sessionViewModelFactory).get(SessionViewModel::class.java)
        sessionLengthRepository = SessionLengthRepository(this)
        bell = Bell(
                context = applicationContext,
                hasPlayed = (savedInstanceState ?: Bundle()).getBoolean(Bell.HAS_PLAYED_KEY)
        )

        // Emit Main Composable
        setContent {
            MainLayout(
                onClick = ::onCircleClicked,
                onRatingClick = ::onRatingClick,
            )
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

    private fun onRatingClick(newSessionLength: Double) {
        sessionLengthRepository.storeSessionLength(newSessionLength)
    }

    private fun onTimerStateChanged(newSessionState: SessionState) {
        when (newSessionState) {
            is Idle -> { }
            is Running -> { bell.reset() }
            is Finished -> { bell.play() }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(Bell.HAS_PLAYED_KEY, bell.hasPlayed)
    }

}
