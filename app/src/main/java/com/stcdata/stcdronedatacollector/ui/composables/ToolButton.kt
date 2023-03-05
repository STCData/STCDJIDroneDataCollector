package org.like.a.fly.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun ToolButton(
    image: Painter,
    isChecked: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    FloatingActionButton(onClick, modifier.alpha(0.69f), containerColor = if (isChecked) Color.White else FloatingActionButtonDefaults.containerColor) {
        Image(painter = image,contentDescription = "")
    }
}