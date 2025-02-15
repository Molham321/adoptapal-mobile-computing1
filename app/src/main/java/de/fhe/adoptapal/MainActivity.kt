package de.fhe.adoptapal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import de.fhe.adoptapal.ui.screens.core.AppScaffold
import de.fhe.adoptapal.ui.theme.AndroidAdoptapalTheme

/**
 * The main activity class that serves as the entry point for the Adoptapal app.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidAdoptapalTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AppScaffold()
                }
            }
        }
    }
}