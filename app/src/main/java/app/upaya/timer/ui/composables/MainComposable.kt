package app.upaya.timer.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.upaya.timer.timer.TimerStates
import app.upaya.timer.timer.TimerViewModel


@ExperimentalAnimationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainComposable(timerViewModel: TimerViewModel, onClick: () -> Unit) {

    val sessionLength = timerViewModel.sessionLengthString.observeAsState("")
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val timerState = timerViewModel.state.observeAsState(TimerStates.WAITING_FOR_START)

    TimerTheme {
        ModalBottomSheetLayout(
                sheetState = sheetState,
                scrimColor = Color(0, 0, 0, 128),
                sheetContent = {
                    Text(
                            text = "Current Session Length: ${sessionLength.value}",
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier.padding(16.dp)
                    )
                }
        ) {
            ConstraintLayout {
                val optionsButton = createRef()
                TimerRing(
                        timerViewModel = timerViewModel,
                        onClick = onClick
                )
                AnimatedVisibility(
                        visible = timerState.value == TimerStates.WAITING_FOR_START,
                        modifier = Modifier
                                .constrainAs(optionsButton) {
                                    top.linkTo(parent.top, margin = 16.dp)
                                    end.linkTo(parent.end, margin = 16.dp)
                                },
                ) {
                    StatsButton(onClick = { sheetState.show() } )
                }
            }
        }
    }

}
