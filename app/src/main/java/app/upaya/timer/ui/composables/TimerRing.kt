package app.upaya.timer.ui.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRippleIndication
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import app.upaya.timer.timer.TimerStates
import app.upaya.timer.timer.TimerViewModel


@ExperimentalMaterialApi
@Composable
fun TimerRing(activated: State<Boolean>, onClick: () -> Unit) {

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val timerViewModel: TimerViewModel = viewModel()
    val timerState by timerViewModel.state.observeAsState()

    when (timerState) {
        TimerStates.WAITING_FOR_START -> if (sheetState.isVisible) sheetState.hide()
        TimerStates.FINISHED -> if (!sheetState.isVisible) sheetState.show()
        else -> { }
    }

    ModalBottomSheetLayout(
            sheetState = sheetState,
            scrimColor = Color(0, 0, 0, 128),
            sheetBackgroundColor = MaterialTheme.colors.background,
            sheetContent = {
                SessionRatingDialog(
                        onClickDown = { timerViewModel.increaseSessionLength() },
                        onClickUp = { timerViewModel.decreaseSessionLength() }
                )
            }
    ) {

        // Ripple effect when clicking ring
        val rippleIndication = rememberRippleIndication(
                bounded = false,
                color = MaterialTheme.colors.primary
        )

        Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .fillMaxSize()
                        .clickable(
                                onClick = onClick,
                                enabled = !activated.value,
                                indication = rippleIndication,
                        )
        ) {

            // Ring
            MaterialRing(
                    size = 200.dp,
                    thickness = 12.dp,
                    depth = 3.dp,
                    color = if (activated.value) Color.White else MaterialTheme.colors.primary
            )

        }

    }

}


@ExperimentalMaterialApi
@Preview
@Composable
fun TimerRingPreview() {
    TimerRing(
            activated = mutableStateOf(false),
            onClick = { }
    )
}
