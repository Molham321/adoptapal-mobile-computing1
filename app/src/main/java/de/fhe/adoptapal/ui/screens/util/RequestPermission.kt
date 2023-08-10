package de.fhe.adoptapal.ui.screens.util

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import de.fhe.adoptapal.ui.screens.map.LOGTAG

/**
 * Composable function to request location permission for displaying a map.
 * Uses the Accompanist Permissions library.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun requestLocationPermission() {
    // Location permission states
    val coarseLocationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val fineLocationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    if (coarseLocationPermissionState.status.isGranted && fineLocationPermissionState.status.isGranted) {
        // location permission granted
        // can show map now
        Log.i(LOGTAG, "Location permissions granted")
    } else {
        Column {
            Text("Um die Karte anzuzeigen, müssen Sie ihren Standort freigeben!")
            Button(onClick = {
                fineLocationPermissionState.launchPermissionRequest()
                coarseLocationPermissionState.launchPermissionRequest()
            }) {
                Text("Standort freigeben")
            }
        }
    }
}