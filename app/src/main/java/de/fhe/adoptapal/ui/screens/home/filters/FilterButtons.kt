package de.fhe.adoptapal.ui.screens.home.filters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R

/**
 * A Composable function that displays a set of filter buttons in a column layout.
 *
 * @param onApplyClicked The callback function to be invoked when the "Anwenden" (Apply) button is clicked.
 * @param onResetClicked The callback function to be invoked when the "Zurücksetzen" (Reset) button is clicked.
 */
@Composable
fun FilterButtons(onApplyClicked: () -> Unit, onResetClicked: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Apply Button
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onApplyClicked() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
            ) {
                Text(
                    text = stringResource(R.string.apply),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }
        // Spacer
        Spacer(modifier = Modifier.height(8.dp))

        // Reset Button
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onResetClicked() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text(
                    text = stringResource(R.string.reset_to_default),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }
    }
}