package app.upaya.timer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ShadowText(text: String) {

    Box(alignment = Alignment.Center) {

        text.let {

            // Text
            Text(
                text = it,
                color = Color(0, 0, 0, 64),
                modifier = Modifier.offset(2.dp, 2.dp)
            )

            // Shadow
            Text(
                text = it,
                color = MaterialTheme.colors.onBackground
            )

        }

    }

}
