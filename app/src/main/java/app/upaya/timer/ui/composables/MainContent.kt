package app.upaya.timer.ui.composables

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.upaya.timer.experiments.Finished
import app.upaya.timer.experiments.Idle
import app.upaya.timer.experiments.Running
import app.upaya.timer.experiments.viewmodel.ExperimentViewModel
import app.upaya.timer.ui.composables.entities.StatsButton
import app.upaya.timer.ui.composables.layouts.TimerLayout
import app.upaya.timer.ui.composables.sheets.ExperimentFeedbackDialog
import app.upaya.timer.ui.composables.sheets.ExperimentationStats
import app.upaya.timer.ui.composables.sheets.ProbeCard
import kotlinx.coroutines.launch


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun MainContent(onClick: () -> Unit) {

    TimerTheme {

        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val coroutineScope = rememberCoroutineScope()
        val experimentViewModel: ExperimentViewModel = viewModel()
        val experimentIsIdle by experimentViewModel.isIdle.observeAsState(true)
        val experimentIsRunning by experimentViewModel.isRunning.observeAsState(false)
        val experimentState by experimentViewModel.state.observeAsState()

        ModalBottomSheetLayout(
                sheetState = sheetState,
                scrimColor = Color(0, 0, 0, 128),
                sheetBackgroundColor = MaterialTheme.colors.background,
                sheetContent = { ExperimentationStats() }
        ) {

            TimerLayout(
                topContent = {
                    AnimatedVisibility(visible = experimentIsIdle) {
                        StatsButton(onClick = {
                            coroutineScope.launch { if (!sheetState.isVisible) sheetState.show() }
                        } )
                    }
                },
                bottomContent = {
                    AnimatedVisibility(
                        visible = !experimentIsRunning,
                        enter = fadeIn(),
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                    ) {
                        when (experimentState) {
                            is Idle -> ProbeCard()
                            is Running -> {}
                            is Finished -> ExperimentFeedbackDialog(
                                onClickDown = { (experimentState as Finished).rateExperiment(1.0) },
                                onClickUp = { (experimentState as Finished).rateExperiment(0.0) } )
                        }
                    }
                }
            ) {

                TimerRing(
                    activated = experimentIsRunning,
                    onClick = onClick
                )

            }

        }  // ModalBottomSheetLayout

    }  // TimerTheme

}  // MainComposable
