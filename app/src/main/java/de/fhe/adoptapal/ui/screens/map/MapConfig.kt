package de.fhe.adoptapal.ui.screens.map

import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings

object MapConfig {
    val properties = MapProperties(
        maxZoomPreference = 18f,
        minZoomPreference = 5f,
        isBuildingEnabled = true,
        isIndoorEnabled = true,
        isMyLocationEnabled = true,
        isTrafficEnabled = true,
        mapType = MapType.TERRAIN
    )

    val uiSettings = MapUiSettings(
        mapToolbarEnabled = false,
        compassEnabled = true,
        rotationGesturesEnabled = true,
        tiltGesturesEnabled = true,
        myLocationButtonEnabled = true,
        zoomGesturesEnabled = true
    )
}