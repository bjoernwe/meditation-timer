package app.upaya.timer.ui.composables.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.min
import androidx.ui.tooling.preview.Preview


@Composable
fun TimerLayout(
    rowWeights: Triple<Float, Float, Float> = Triple(2f, 5f, 3f),
    topContent: @Composable () -> Unit = {},
    bottomContent: @Composable () -> Unit = {},
    centerContent: @Composable () -> Unit = {}
) {
    Column(Modifier
        .fillMaxWidth()
        .background(Color.Red)
    ) {

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .weight(rowWeights.first)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background),
        ) {
            topContent()
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(rowWeights.second)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background),
        ) {
            BoxWithConstraints {
                val maxSize = min(maxWidth, maxHeight)
                Box(modifier = Modifier
                    .size(maxSize.div(1.75f), maxSize.div(1.75f))
                    .background(Color.Yellow)
                ) {
                    centerContent()
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .weight(rowWeights.third)
                .fillMaxWidth()
                .background(MaterialTheme.colors.background),
        ) {
            bottomContent()
        }
    }
}


@Preview
@Composable
fun MainLayoutPreview() {
    TimerLayout()
}
