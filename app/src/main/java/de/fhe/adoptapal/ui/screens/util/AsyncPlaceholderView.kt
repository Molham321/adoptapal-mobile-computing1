package de.fhe.adoptapal.ui.screens.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun FullscreenPlaceholderView(
    text: String,
    icon: ImageVector
) {
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.Center)
        ) {
            Icon(
                icon,
                text,
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .scale(scale = 3f)
                    .padding(40.dp)
            )
            Text(
                text,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}