package app.upaya.timer

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.session.*
import app.upaya.timer.session.repository.SessionRepository
import app.upaya.timer.session.repository.room.SessionLogDatabase
import app.upaya.timer.session.viewmodel.SessionViewModel
import app.upaya.timer.session.viewmodel.SessionViewModelFactory
import app.upaya.timer.settings.SessionLengthRepository
import app.upaya.timer.ui.Bell
import app.upaya.timer.ui.composables.MainLayout


class MainActivity : AppCompatActivity() {

    private lateinit var bell: Bell
    private lateinit var sessionHandler: ISessionHandler
    private lateinit var sessionViewModel: SessionViewModel
    private lateinit var sessionLengthRepository: SessionLengthRepository

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        /**
         * Late Inits
         */

        sessionLengthRepository = SessionLengthRepository(this)
        val initialSessionLength = sessionLengthRepository.loadSessionLength()
        val sessionLogDao = SessionLogDatabase.getInstance(this).sessionLogDao
        val sessionRepository = SessionRepository(sessionLogDao)
        sessionHandler = SessionHandler(
            sessionRepository = sessionRepository,
            initialSessionLength = initialSessionLength,
        )

        val sessionViewModelFactory = SessionViewModelFactory(this)
        sessionViewModel = ViewModelProvider(this, sessionViewModelFactory).get(SessionViewModel::class.java)

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
        sessionViewModel.state.observe(this) { onSessionStateChanged(it) }
        sessionViewModel.sessionLength.observe(this) { onSessionLengthChanged(it) }
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

    private fun onSessionStateChanged(newSessionState: SessionState?) {
        when (newSessionState) {
            is Idle -> { }
            is Running -> { bell.reset() }
            is Finished -> { bell.play() }
        }
    }

    private fun onSessionLengthChanged(newSessionLength: Double) {
        sessionLengthRepository.storeSessionLength(newSessionLength)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(Bell.HAS_PLAYED_KEY, bell.hasPlayed)
    }

}
