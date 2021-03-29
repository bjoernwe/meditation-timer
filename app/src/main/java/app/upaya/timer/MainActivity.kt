package app.upaya.timer

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.session.*
import app.upaya.timer.experiments.viewmodel.ExperimentViewModel
import app.upaya.timer.experiments.viewmodel.ExperimentViewModelFactory
import app.upaya.timer.settings.SessionLengthRepository
import app.upaya.timer.ui.Bell
import app.upaya.timer.ui.composables.MainLayout


class MainActivity : AppCompatActivity() {

    private lateinit var bell: Bell
    private lateinit var experimentViewModel: ExperimentViewModel
    private lateinit var sessionLengthRepository: SessionLengthRepository

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        /**
         * Late Inits
         */

        val sessionViewModelFactory = ExperimentViewModelFactory(this)
        experimentViewModel = ViewModelProvider(this, sessionViewModelFactory).get(
            ExperimentViewModel::class.java)
        sessionLengthRepository = SessionLengthRepository(this)

        bell = Bell(
                context = applicationContext,
                hasPlayed = (savedInstanceState ?: Bundle()).getBoolean(Bell.HAS_PLAYED_KEY)
        )

        /**
         * Emit Main Composable
         */

        setContent { MainLayout(onClick = ::onCircleClicked) }


        /**
         * Register Event Callbacks
         */

        experimentViewModel.state.observe(this) { onSessionStateChanged(it) }
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
        if (experimentViewModel.state.value is Idle) {
            (experimentViewModel.state.value as? Idle)?.startSession()
            bell.vibrate(50, 100)
        }
    }

    private fun onSessionStateChanged(newSessionState: SessionState?) {
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
