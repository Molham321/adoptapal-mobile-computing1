package de.fhe.adoptapal.ui.screens.map

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

val LOGTAG = "MAPS"

private const val DEFAULT_ZOOM = 17f
private const val DEFAULT_LAT = 50.985248
private const val DEFAULT_LONG = 11.042582

/**
 * Maps Compose: https://github.com/googlemaps/android-maps-compose
 */
@Composable
fun MapScreen() {
    val mapCenterPosition = LatLng(DEFAULT_LAT, DEFAULT_LONG)
    Log.i(LOGTAG, "remember state")
    val mapCenterMarkerState = rememberMarkerState(position = mapCenterPosition)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(mapCenterPosition, DEFAULT_ZOOM)
    }
    Log.i(LOGTAG, "cameraPosition")
    val mapProperties by remember { mutableStateOf(MapConfig.properties) }
    val mapUiSettings by remember { mutableStateOf(MapConfig.uiSettings) }
    Log.i(LOGTAG, "UI SETTONGS")
    Column {
        Log.i(LOGTAG, "COLUIN")
        MarkerDebugView(mapCenterMarkerState)
        Log.i(LOGTAG, "DEBUG")
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = mapUiSettings,
            onPOIClick = { println("POI clicked: ${it.name}") }
        ) {
            Log.i(LOGTAG, "MAP")
            CafeAquaMarker {
                println("${it.title} was clicked - Position ${it.position}")
                false
            }
            Log.i(LOGTAG, "AQUA")
            FheMarker(markerState = mapCenterMarkerState)
            FheArea(zIndex = 2f)
            Log.i(LOGTAG, "FHE")
            MapCircle(center = mapCenterPosition, zIndex = 1f)
            Log.i(LOGTAG, "END")
        }
    }
}
