package app.upaya.timer.ui.composables.entities

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ShadowText(text: String, modifier: Modifier = Modifier) {

    Box(contentAlignment = Alignment.Center) {

        text.let {

            // Shadow
            Text(
                text = it,
                color = Color(0, 0, 0, 64),
                modifier = modifier.offset(2.dp, 2.dp)
            )

            // Text
            Text(
                text = it,
                color = Color.White
            )

        }

    }

}
