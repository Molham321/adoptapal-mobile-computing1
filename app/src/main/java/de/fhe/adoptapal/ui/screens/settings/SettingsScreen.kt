package de.fhe.adoptapal.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.ui.screens.core.LocalScaffoldState

/**
 * A Composable function that displays the settings screen. It provides user settings and displays a snackbar
 * based on the status of a save operation.
 *
 * @param vm The [SettingsScreenViewModel] used to manage user settings.
 * @param modifier The [Modifier] to be applied to the Composable.
 */
@Composable
fun SettingsScreen(vm: SettingsScreenViewModel, modifier: Modifier = Modifier) {

    // Retrieve the user data and save operation state from the view model
    val user = remember { vm.user }
    val saveState by remember(vm) { vm.saveFeedbackFlow }
        .collectAsState(AsyncOperation.undefined())

    // Retrieve the scaffold state for showing snackbar
    val scaffoldState = LocalScaffoldState.current

    // Show a snackbar when the save operation state changes
    LaunchedEffect(saveState) {
        if (saveState.status != AsyncOperationState.UNDEFINED) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "${saveState.message}...",
                duration = SnackbarDuration.Short
            )
        }
    }

    // Render the UI based on user login status
    if (user.value != null) {
        Column() {
            Settings(user.value!!) {
                vm.updateUser(it)
            }
        }
    } else {
        Column() {
            // Display a message for unauthenticated users
            Text(
                text = "Du bist nicht angemeldet! Melde dich erstmal an.",
                modifier = modifier.padding(16.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
        }
    }

}