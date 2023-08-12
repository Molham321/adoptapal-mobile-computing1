package de.fhe.adoptapal.ui.screens.util

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import de.fhe.adoptapal.R
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import de.fhe.adoptapal.ui.screens.map.LOGTAG
import org.koin.androidx.compose.getKoin

/**
 * Composable function to request location permission for displaying a map.
 * Uses the Accompanist Permissions library.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission() {

    val navigationManager by getKoin().inject<NavigationManager>()

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

        navigationManager.navigate(Screen.Map.navigationCommand())
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(60.dp, 140.dp, 60.dp, 0.dp),
        ) {
            Text(
                text = "Um die Karte anzuzeigen, müssen Sie ihren Standort freigeben!",
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 25.sp,
                color = colorResource(id = R.color.black),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClick = {
                    fineLocationPermissionState.launchPermissionRequest()
                    coarseLocationPermissionState.launchPermissionRequest()
                }
            ) {
                Text("Standort freigeben")
            }
        }
    }
}