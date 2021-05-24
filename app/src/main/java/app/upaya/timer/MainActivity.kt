package app.upaya.timer

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModelProvider
import app.upaya.timer.experiments.ExperimentState
import app.upaya.timer.experiments.Finished
import app.upaya.timer.experiments.Idle
import app.upaya.timer.experiments.Running
import app.upaya.timer.experiments.viewmodel.ExperimentViewModel
import app.upaya.timer.experiments.viewmodel.ExperimentViewModelFactory
import app.upaya.timer.ui.Bell
import app.upaya.timer.ui.composables.MainContent


class MainActivity : AppCompatActivity() {

    private lateinit var bell: Bell
    private lateinit var experimentViewModel: ExperimentViewModel

    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        /**
         * Late Inits
         */

        val experimentViewModelFactory = ExperimentViewModelFactory(this)
        experimentViewModel = ViewModelProvider(this, experimentViewModelFactory)
            .get(ExperimentViewModel::class.java)

        bell = Bell(
                context = applicationContext,
                hasPlayed = (savedInstanceState ?: Bundle()).getBoolean(Bell.HAS_PLAYED_KEY)
        )

        /**
         * Emit Main Composable
         */

        setContent { MainContent(onClick = ::onCircleClicked) }


        /**
         * Register Event Callbacks
         */

        experimentViewModel.state.observe(this) { onExperimentStateChanged(it) }
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
            (experimentViewModel.state.value as? Idle)?.startExperiment()
            bell.vibrate(50, 100)
        }
    }

    private fun onExperimentStateChanged(newExperimentState: ExperimentState?) {
        when (newExperimentState) {
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
