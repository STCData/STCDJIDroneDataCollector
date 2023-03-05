package org.like.a.fly.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun HorizontalProgressBarWithPercentLabel(
    modifier: Modifier = Modifier,
            value: Float = .84f,
        barColor: Color = Color.DarkGray,
        barTrackColor: Color = Color.Gray, labelColor: Color = Color.Red,
        ) {
    val progress = when (value) {
        in 0f..1f -> value
        else -> 0f
    }
    Box(modifier = modifier) {
        LinearProgressIndicator(progress = progress, color = barColor, trackColor = barTrackColor, modifier = Modifier.fillMaxHeight())
        Box(modifier = Modifier.align(Alignment.Center)) {
            PercentMonoLabel(value, labelColor)
        }
    }
}
