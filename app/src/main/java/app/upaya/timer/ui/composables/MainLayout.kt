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
import app.upaya.timer.session.Finished
import app.upaya.timer.session.Idle
import app.upaya.timer.session.Running
import app.upaya.timer.session.SessionViewModel
import app.upaya.timer.ui.composables.entities.StatsButton
import app.upaya.timer.ui.composables.sheets.SessionHintsCard


@ExperimentalAnimationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainLayout(onClick: () -> Unit) {

    TimerTheme {

        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val sessionViewModel: SessionViewModel = viewModel()
        val timerState by sessionViewModel.state.observeAsState()

        if (timerState is Finished && !sheetState.isVisible) sheetState.show()

        ModalBottomSheetLayout(
                sheetState = sheetState,
                scrimColor = Color(0, 0, 0, 128),
                sheetBackgroundColor = MaterialTheme.colors.background,
                sheetContent = {
                    when (timerState) {
                        is Idle -> SessionStats()
                        is Running -> Text("There is nothing to see here!")
                        is Finished -> SessionRatingDialog(
                                onClickDown = { sheetState.hide { (timerState as? Finished)?.increaseSessionLength() } },
                                onClickUp = { sheetState.hide { (timerState as? Finished)?.decreaseSessionLength() } }
                        )
                    }
                }
        ) {

            ConstraintLayout {

                TimerRing(
                        activated = sessionViewModel.isRunning.observeAsState(false),
                        onClick = onClick
                )

                val (hintCard, statsButton) = createRefs()

                AnimatedVisibility(
                        visible = sessionViewModel.isIdle.observeAsState(false).value,
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
                        visible = sessionViewModel.isIdle.observeAsState(false).value,
                        enter = fadeIn() + slideInVertically({it/2}),
                        modifier =  Modifier
                                .constrainAs(hintCard)
                                {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom)
                                }
                                .padding(24.dp)
                                .fillMaxWidth()
                ) {
                    SessionHintsCard()
                }

            }  // ConstraintLayout

        }  // ModalBottomSheetLayout

    }  // TimerTheme

}  // MainComposable
