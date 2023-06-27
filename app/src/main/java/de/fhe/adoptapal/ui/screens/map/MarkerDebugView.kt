package de.fhe.adoptapal.ui.screens.map

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.MarkerState
import java.util.Locale


@Composable
fun MarkerDebugView(markerState: MarkerState) {
    Text(
        text = "Marker State:\n" +
                "dragged: ${markerState.dragState}\n" + "position: " +
                String.format(
                    locale = Locale.getDefault(), "%.8f",
                    markerState.position.latitude
                ) + ", " +
                String.format(
                    locale = Locale.getDefault(), "%.8f",
                    markerState.position.longitude
                ),
        modifier = Modifier.fillMaxWidth()
    )
}
