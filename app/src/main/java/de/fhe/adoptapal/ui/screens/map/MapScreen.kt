package de.fhe.adoptapal.ui.screens.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import de.fhe.adoptapal.android_core.LoggerFactory
import de.fhe.adoptapal.ui.screens.util.RequestLocationPermission

const val LOGTAG = "MAPS"

private const val DEFAULT_ZOOM = 17f

/**
 * base map composable
 * open Map or request Location permission first
 */
@Composable
fun MapScreen(vm: MapScreenViewModel) {

    val context = LocalContext.current
    val mapCenterPosition = remember { mutableStateOf<LatLng?>(null) }

    mapCenterPosition.value = requestLocation(context)

    if (mapCenterPosition.value != null) {
        Map(mapCenterPosition.value!!, vm)
    }
}


/**
 * google maps screen
 * Maps Compose: https://github.com/googlemaps/android-maps-compose
 */
@Composable
fun Map(mapCenterPosition: LatLng, vm: MapScreenViewModel) {

    val logger = LoggerFactory.getLogger()

    val userList = remember { vm.userList }

    logger.info(LOGTAG, "remember state")
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(mapCenterPosition, DEFAULT_ZOOM)
    }
    logger.info(LOGTAG, "cameraPosition")
    val mapProperties by remember { mutableStateOf(MapConfig.properties) }
    val mapUiSettings by remember { mutableStateOf(MapConfig.uiSettings) }

    logger.info(LOGTAG, "UI SETTINGS")
    Column {
        logger.info(LOGTAG, "DEBUG")
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = mapUiSettings,
            onPOIClick = { println("POI clicked: ${it.name}") }
        ) {

            // add animal supplier as Location markers to map
            userList.value.forEach { user ->
                if (user.address != null) {
                    val latLng = LatLng(user.address!!.latitude, user.address!!.longitude)
                    SimpleMarker(latLng = latLng, title = user.name) {
                        vm.navigateToUser(user.id)
                    }
                }
            }
        }
    }
}

/**
 * request Location and permission if not already granted
 */
@Composable
private fun requestLocation(context: Context): LatLng? {

    val logger = LoggerFactory.getLogger()

    val mapCenterPosition = remember { mutableStateOf<LatLng?>(null) }
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        RequestLocationPermission()
    }
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            if (location != null) {
                logger.info(
                    LOGTAG,
                    "Location in MapScreen is not null with lat: ${location.latitude} and long: ${location.longitude}"
                )
                mapCenterPosition.value = LatLng(location.latitude, location.longitude)
            } else {
                logger.info(LOGTAG, "Location was null")
            }
        }

    return mapCenterPosition.value
}
