package app.upaya.timer.ui

import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.upaya.timer.timer.TimerViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainComposable(timerViewModel: TimerViewModel, onClick: () -> Unit) {

    val sessionLength = timerViewModel.sessionLengthString.observeAsState("")
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    TimerTheme {
        ModalBottomSheetLayout(
                sheetState = sheetState,
                scrimColor = Color(0, 0, 0, 128),
                sheetContent = {
                    Text(
                            text = "Current Session Length: ${sessionLength.value}",
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
                StatsButton(onClick = { sheetState.show() },
                        modifier = Modifier
                                .constrainAs(optionsButton) {
                                    top.linkTo(parent.top, margin = 8.dp)
                                    end.linkTo(parent.end, margin = 8.dp)
                                },
                )
            }
        }
    }

}
