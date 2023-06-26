package de.fhe.adoptapal.ui.screens.map

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon

@Composable
fun FheMarker(markerState: MarkerState) {
    MarkerInfoWindowContent(
        state = markerState,
        title = "FHE Marker",
        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE),
        draggable = true,
    ) {
        Text(it.title ?: "Title", color = Color.Red)
    }
}

private const val CAFE_AQUA_LAT = 50.985542
private const val CAFE_AQUA_LONG = 11.043757

@Composable
fun CafeAquaMarker(onClick: (Marker) -> Boolean) {
    Marker(
        state = MarkerState(position = LatLng(CAFE_AQUA_LAT, CAFE_AQUA_LONG)),
        title = "Aqua",
        onClick = onClick
    )
}

@Composable
fun MapCircle(center: LatLng, zIndex: Float) {
    Circle(
        center = center,
        strokeColor = Color.DarkGray,
        strokeWidth = 2f,
        fillColor = Color.Green,
        radius = 150.0,
        zIndex = zIndex
    )
}

@Composable
fun FheArea(zIndex: Float) {
    Polygon(
        points = listOf(
            LatLng(50.985153, 11.040897),
            LatLng(50.984323, 11.041778),
            LatLng(50.985187, 11.043796),
            LatLng(50.986058, 11.042796)
        ),
        fillColor = Color.LightGray,
        strokeColor = Color.DarkGray,
        strokeWidth = 2f,
        zIndex = zIndex
    )
}