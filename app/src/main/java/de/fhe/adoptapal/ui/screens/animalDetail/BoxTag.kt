package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Composable function to display a rounded corner tag with text inside.
 *
 * @param text Text to display in the tag.
 * @param color Background color of the tag.
 */
@Composable
fun BoxTag(text: String, color: Color = Color.Red) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.08f)) // Adjust the alpha value for transparency
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(12.dp, 6.dp),
            style = MaterialTheme.typography.caption,
            color = color
        )
    }
}
