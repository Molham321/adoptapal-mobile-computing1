package de.fhe.adoptapal.ui.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.ui.screens.core.LocalScaffoldState
import de.fhe.adoptapal.ui.screens.sharedComponents.InputField
import de.fhe.adoptapal.ui.screens.sharedComponents.PasswordInputField

/**
 * Composable function to display the login screen.
 *
 * @param vm The ViewModel associated with the login screen.
 * @param modifier Modifier to be applied to the entire screen.
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(vm: LoginScreenViewModel, modifier: Modifier = Modifier) {
    // Collect the save state from the ViewModel
    val saveState by remember(vm) { vm.saveFeedbackFlow }
        .collectAsState(AsyncOperation.undefined())

    // State variables for user email and password fields
    var userEmailTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var userPasswordTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    // State variables for error messages
    var userEmailError by remember { mutableStateOf("") }
    var userPasswordError by remember { mutableStateOf("") }

    // State variable for editing state
    var editingState by remember { mutableStateOf(false) }

    // Scaffold state for showing snackbar
    val scaffoldState = LocalScaffoldState.current

    // Show snackbar when save state changes
    LaunchedEffect(saveState) {
        if (saveState.status != AsyncOperationState.UNDEFINED) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "${saveState.message}...",
                duration = SnackbarDuration.Short
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(160.dp))
        Text(
            text = "Willkommen zurück!",
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 0.dp),
            fontSize = 25.sp,
            color = colorResource(id = R.color.black),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(20.dp))
        // User email input field
        InputField(
            text = userEmailTextFieldValue,
            editing = true,
            onTextChange = { newValue ->
                userEmailTextFieldValue = newValue
                userEmailError = ""
            },
            inputPlaceholder = "Email"
        )

        if (userEmailError.isNotBlank()) {
            Text(
                text = userEmailError,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }

        // User password input field
        PasswordInputField(
            text = userPasswordTextFieldValue,
            editing = true,
            onTextChange = { newValue ->
                userPasswordTextFieldValue = newValue
                userPasswordError = ""
            },
            inputPlaceholder = "Passwort"
        )

        if (userPasswordError.isNotBlank()) {
            Text(
                text = userPasswordError,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }
        Spacer(modifier = modifier.height(20.dp))
        // Login button
        Button(
            onClick = {
                val isEmailValid = vm.validateEmail(userEmailTextFieldValue.text)
                val isPasswordValid = vm.validatePassword(userPasswordTextFieldValue.text)

                if (isEmailValid && isPasswordValid) {
                    vm.login(userEmailTextFieldValue.text, userPasswordTextFieldValue.text)
                    userEmailTextFieldValue = TextFieldValue("")
                    userPasswordTextFieldValue = TextFieldValue("")
                    editingState = false
                } else {
                    if (!isEmailValid) {
                        userEmailError = "Ungültige Email"
                    }
                    if (!isPasswordValid) {
                        userPasswordError = "Das von dir eingegebene Passwort ist falsch"
                    }
                }

            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .width(250.dp)
                .padding(16.dp, 8.dp, 16.dp, 8.dp),
        ) {
            Text(text = "Anmeldung")
        }

        // Register button
        Button(
            onClick = { vm.navigateToRegister() },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            elevation = null,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp, 16.dp, 8.dp)
        ) {
            Text(
                text = "Noch kein Mitglied? zur Registrierung",
                textDecoration = TextDecoration.Underline
            )
        }
    }
}
