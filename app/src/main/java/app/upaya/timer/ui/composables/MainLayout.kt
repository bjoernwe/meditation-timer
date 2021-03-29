package app.upaya.timer.ui.composables

import androidx.compose.animation.*
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import app.upaya.timer.session.*
import app.upaya.timer.experiments.viewmodel.ExperimentViewModel
import app.upaya.timer.ui.composables.entities.StatsButton
import app.upaya.timer.ui.composables.sheets.ExperimentFeedbackDialog
import app.upaya.timer.ui.composables.sheets.ExperimentationStats
import app.upaya.timer.ui.composables.sheets.ProbeCard


@ExperimentalAnimationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainLayout(onClick: () -> Unit) {

    TimerTheme {

        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val experimentViewModel: ExperimentViewModel = viewModel()
        val experimentState by experimentViewModel.state.observeAsState()

        if (experimentState is Finished && !sheetState.isVisible) sheetState.show()

        ModalBottomSheetLayout(
                sheetState = sheetState,
                scrimColor = Color(0, 0, 0, 128),
                sheetBackgroundColor = MaterialTheme.colors.background,
                sheetContent = {
                    when (experimentState) {
                        is Idle -> ExperimentationStats()
                        is Running -> Text("There is nothing to see here!")
                        is Finished -> ExperimentFeedbackDialog(
                                onClickDown = { sheetState.hide {
                                    (experimentState as Finished).rateSession(1.0)
                                } },
                                onClickUp = { sheetState.hide {
                                    (experimentState as Finished).rateSession(0.0)
                                } }
                        )
                    }
                }
        ) {

            ConstraintLayout {

                TimerRing(
                        activated = experimentViewModel.isRunning.observeAsState(false),
                        onClick = onClick
                )

                val (probeCard, statsButton) = createRefs()

                AnimatedVisibility(
                        visible = experimentViewModel.isIdle.observeAsState(false).value,
                        modifier = Modifier
                                .constrainAs(statsButton)
                                {
                                    top.linkTo(parent.top, margin = 16.dp)
                                    end.linkTo(parent.end, margin = 16.dp)
                                },
                ) {
                    StatsButton(onClick = { if (!sheetState.isVisible) sheetState.show() })
                }

                AnimatedVisibility(
                        visible = experimentViewModel.isIdle.observeAsState(false).value,
                        enter = fadeIn(),
                        modifier =  Modifier
                                .constrainAs(probeCard)
                                {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom)
                                }
                                .padding(24.dp)
                                .fillMaxWidth()
                ) {
                    ProbeCard()
                }

            }  // ConstraintLayout

        }  // ModalBottomSheetLayout

    }  // TimerTheme

}  // MainComposable
