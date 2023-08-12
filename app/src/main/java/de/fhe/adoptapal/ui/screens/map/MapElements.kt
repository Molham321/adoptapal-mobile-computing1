package de.fhe.adoptapal.ui.screens.map

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

/**
 * simple Map Location marker
 */
@Composable
fun SimpleMarker(latLng: LatLng, title: String, onClick: (Marker) -> Unit) {
    Marker(
        state = MarkerState(position = latLng),
        title = title,
        onInfoWindowClick = onClick
    )
}
