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
import app.upaya.timer.experiments.repositories.length.ExperimentLengthRepository
import app.upaya.timer.ui.Bell
import app.upaya.timer.ui.composables.MainLayout


class MainActivity : AppCompatActivity() {

    private lateinit var bell: Bell
    private lateinit var experimentViewModel: ExperimentViewModel
    private lateinit var experimentLengthRepository: ExperimentLengthRepository

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        /**
         * Late Inits
         */

        val experimentViewModelFactory = ExperimentViewModelFactory(this)
        experimentViewModel = ViewModelProvider(this, experimentViewModelFactory)
            .get(ExperimentViewModel::class.java)
        experimentLengthRepository = ExperimentLengthRepository(this)

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
