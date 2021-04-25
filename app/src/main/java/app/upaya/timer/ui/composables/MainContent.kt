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
import app.upaya.timer.session.*
import app.upaya.timer.session.viewmodel.SessionViewModel
import app.upaya.timer.ui.composables.entities.StatsButton
import app.upaya.timer.ui.composables.layouts.TimerLayout
import app.upaya.timer.ui.composables.sheets.SessionHintsCard
import app.upaya.timer.ui.composables.sheets.SessionRatingDialog
import app.upaya.timer.ui.composables.sheets.SessionStats
import kotlinx.coroutines.launch


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun MainContent(onClick: () -> Unit) {

    TimerTheme {

        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val coroutineScope = rememberCoroutineScope()
        val sessionViewModel: SessionViewModel = viewModel()
        val sessionIsIdle by sessionViewModel.isIdle.observeAsState(true)
        val sessionIsRunning by sessionViewModel.isRunning.observeAsState(false)
        val sessionState by sessionViewModel.state.observeAsState()

        ModalBottomSheetLayout(
                sheetState = sheetState,
                scrimColor = Color(0, 0, 0, 128),
                sheetBackgroundColor = MaterialTheme.colors.background,
                sheetContent = { SessionStats() }
        ) {

            TimerLayout(
                topContent = {

                    AnimatedVisibility(visible = sessionIsIdle) {
                        StatsButton(onClick = {
                            coroutineScope.launch { if (!sheetState.isVisible) sheetState.show() }
                        } )
                    }
                },
                bottomContent = {
                    AnimatedVisibility(
                        visible = !sessionIsRunning,
                        enter = fadeIn(),
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                    ) {
                        when (sessionState) {
                            is Idle -> SessionHintsCard()
                            is Running -> {}
                            is Finished -> SessionRatingDialog(
                                onClickDown = { (sessionState as Finished).rateSession(1.0) },
                                onClickUp = { (sessionState as Finished).rateSession(0.0) } )
                        }
                    }
                }
            ) {

                TimerRing(
                    activated = sessionIsRunning,
                    onClick = onClick
                )

            }

        }  // ModalBottomSheetLayout

    }  // TimerTheme

}  // MainComposable
