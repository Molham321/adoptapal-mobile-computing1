package de.fhe.adoptapal.ui.screens.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.maps.android.compose.GoogleMap
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

    var userLocation = de.fhe.adoptapal.domain.Location(0.0, 0.0)

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(
        LocalContext.current
    )

    if (ActivityCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->

            Log.i(LOGTAG, "Location $location")
            if (location != null) {
                userLocation.latitude = location.latitude
                userLocation.longitude = location.longitude
            }

            // Got last known location. In some rare situations this can be null.
        }

    fusedLocationClient.getCurrentLocation(
        LocationRequest.PRIORITY_HIGH_ACCURACY,
        object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                CancellationTokenSource().token

            override fun isCancellationRequested() = false
        })
        .addOnSuccessListener { location: Location? ->


            Log.i(LOGTAG, "Location new: $location")
            if (location != null) {
                userLocation.latitude = location.latitude
                userLocation.longitude = location.longitude
            }
        }

    val mapCenterPosition = LatLng(userLocation.latitude, userLocation.longitude)
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
