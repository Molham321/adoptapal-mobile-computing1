package de.fhe.adoptapal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import de.fhe.adoptapal.ui.screens.core.AppScaffold
import de.fhe.adoptapal.ui.theme.AndroidAdoptapalTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Log.i("Test", BuildConfig.LAT_LONG_API_KEY)
//        Log.i("Test", BuildConfig.MAPS_API_KEY)

        setContent {
            AndroidAdoptapalTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AppScaffold()
                }
            }
        }
    }
}