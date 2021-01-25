package app.upaya.timer.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import app.upaya.timer.timer.TimerStates
import app.upaya.timer.timer.TimerViewModel


@ExperimentalAnimationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainLayout(onClick: () -> Unit) {

    TimerTheme {

        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val timerViewModel: TimerViewModel = viewModel()
        val timerState = timerViewModel.state.observeAsState()

        // Allow dismissal of rating dialog. Work-around since SheetState's confirmStateChange
        // doesn't seem to be working.
        if (timerState.value == TimerStates.FINISHED && !sheetState.isVisible) timerViewModel.keepSessionLength()

        ModalBottomSheetLayout(
                sheetState = sheetState,
                scrimColor = Color(0, 0, 0, 128),
                sheetBackgroundColor = MaterialTheme.colors.background,
                sheetContent = {
                    when (timerState.value) {
                        TimerStates.WAITING_FOR_START -> SessionStats()
                        TimerStates.FINISHED -> SessionRatingDialog(
                                onClickDown = { sheetState.hide { timerViewModel.increaseSessionLength() } },
                                onClickUp = { sheetState.hide { timerViewModel.decreaseSessionLength() } }
                        )
                        else -> { }
                    }
                }
        ) {

            ConstraintLayout {

                TimerRing(
                        activated = timerViewModel.isRunning.observeAsState(false),
                        onClick = onClick
                )

                val optionsButton = createRef()

                AnimatedVisibility(
                        visible = timerViewModel.isWaiting.observeAsState(false).value,
                        modifier = Modifier
                                .constrainAs(optionsButton) {
                                    top.linkTo(parent.top, margin = 16.dp)
                                    end.linkTo(parent.end, margin = 16.dp)
                                },
                ) {
                    StatsButton(onClick = { if (!sheetState.isVisible) sheetState.show() } )
                }

            }  // ConstraintLayout

        }  // ModalBottomSheetLayout

    }  // TimerTheme

}  // MainComposable
