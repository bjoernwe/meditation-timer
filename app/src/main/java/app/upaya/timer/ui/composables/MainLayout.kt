package app.upaya.timer.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import app.upaya.timer.timer.Finished
import app.upaya.timer.timer.Idle
import app.upaya.timer.timer.Running
import app.upaya.timer.timer.TimerViewModel


@ExperimentalAnimationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainLayout(onClick: () -> Unit) {

    TimerTheme {

        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val timerViewModel: TimerViewModel = viewModel()
        val timerState by timerViewModel.state.observeAsState()

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
                        activated = timerViewModel.isRunning.observeAsState(false),
                        onClick = onClick
                )

                val optionsButton = createRef()

                AnimatedVisibility(
                        visible = timerViewModel.idIdle.observeAsState(false).value,
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
