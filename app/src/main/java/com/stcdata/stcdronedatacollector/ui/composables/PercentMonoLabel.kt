package org.like.a.fly.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PercentMonoLabel(value: Float = -1f, textColor: Color = Color.Magenta) {
    val formattedValue = when (value) {
        in 0f..1f -> String.format("%02d", (value * 100).toInt())
        1f -> "1ꚙ"
        else -> "XX"
    }

    Row(modifier = Modifier.padding(10.dp)) {
        Text(".", style = TextStyle(fontStyle = FontStyle.Italic, color = textColor))
        Spacer(Modifier.width(0.dp))
        Text(formattedValue, style = TextStyle(fontFamily = FontFamily.Monospace, color = textColor))
    }
}


