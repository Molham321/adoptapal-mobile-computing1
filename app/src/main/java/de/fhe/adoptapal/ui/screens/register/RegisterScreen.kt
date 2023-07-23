package de.fhe.adoptapal.ui.screens.register

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.ui.screens.core.LocalScaffoldState
import de.fhe.adoptapal.ui.screens.sharedComponents.PasswordInputField

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegisterScreen(vm: RegisterScreenViewModel, modifier: Modifier = Modifier) {

    val saveState by remember(vm) { vm.saveFeedbackFlow }
        .collectAsState(AsyncOperation.undefined())

    var userNameTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var userEmailTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var userPhoneNumberTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var userPasswordTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var userConfirmPasswordTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    var userNameError by remember { mutableStateOf(("")) }
    var userEmailError by remember { mutableStateOf("") }
    var userPhoneNumberError by remember { (mutableStateOf("")) }
    var userPasswordError by remember { mutableStateOf("") }
    var userConfirmPasswordError by remember { (mutableStateOf("")) }

    var editingState by remember { mutableStateOf(false) }

    val scaffoldState = LocalScaffoldState.current

    LaunchedEffect(saveState) {
        if (saveState.status != AsyncOperationState.UNDEFINED) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "${saveState.message}...",
                duration = SnackbarDuration.Short
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Willkommen bei AdoptAPal!",

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 0.dp),
            fontSize = 25.sp,
            color = colorResource(id = R.color.black),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        InputField(
            text = userNameTextFieldValue,
            editing = true,
            onTextChange = { newValue ->
                userNameTextFieldValue = newValue
                userNameError = ""
                           },
            inputPlaceholder = "Name"
        )

        if (userNameError.isNotBlank()) {
            Text(
                text = userNameError,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }

        Text(
            text = "*ihr Name oder der Name ihrer Einrichtung",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 0.dp),
            fontSize = 10.sp,
            color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center
        )

        InputField(
            text = userEmailTextFieldValue,
            editing = true,
            onTextChange = { newValue ->
                userEmailTextFieldValue = newValue
                userEmailError = ""
                           },
            inputPlaceholder = stringResource(id = R.string.email)
        )
        if (userEmailError.isNotBlank()) {
            Text(
                text = userEmailError,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }

        InputField(
            text = userPhoneNumberTextFieldValue,
            editing = true,
            onTextChange = { newValue ->
                userPhoneNumberTextFieldValue = newValue
                userPhoneNumberError = ""
                           },
            inputPlaceholder = "Telefonnummer"
        )

        if (userPhoneNumberError.isNotBlank()) {
            Text(
                text = userPhoneNumberError,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }

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

        PasswordInputField(
            text = userConfirmPasswordTextFieldValue,
            editing = true,
            onTextChange = { newValue ->
                userConfirmPasswordTextFieldValue = newValue
                userConfirmPasswordError = ""
                           },
            inputPlaceholder = "Passwort wiederholen"
        )

        if (userConfirmPasswordError.isNotBlank()) {
            Text(
                text = userConfirmPasswordError,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {

                val isNameValid = vm.validateName(userNameTextFieldValue.text)
                val isEmailValid = vm.validateEmail(userEmailTextFieldValue.text)
                val isPhoneNumberValid = vm.validatePhoneNumber(userPhoneNumberTextFieldValue.text)
                val isPasswordValid = vm.validatePassword(userPasswordTextFieldValue.text)
                val isConfirmPasswordValid = vm.validateConfirmPassword(userPasswordTextFieldValue.text, userConfirmPasswordTextFieldValue.text)

                if(isNameValid &&
                        isEmailValid &&
                        isPhoneNumberValid &&
                        isPasswordValid &&
                        isConfirmPasswordValid) {
                    vm.addUser(
                        userNameTextFieldValue.text,
                        userEmailTextFieldValue.text,
                        userPhoneNumberTextFieldValue.text
                    )
                    // Clear Form
                    userNameTextFieldValue = TextFieldValue("")
                    userEmailTextFieldValue = TextFieldValue("")
                    userPhoneNumberTextFieldValue = TextFieldValue("")
                    userPasswordTextFieldValue = TextFieldValue("")
                    userConfirmPasswordTextFieldValue = TextFieldValue("")
                    // To hide keyboard
                    editingState = false
                } else {
                    if (!isNameValid) {
                        userNameError = "Ungültiger Name"
                    }
                    if (!isEmailValid) {
                        userEmailError = "Ungültige Email"
                    }
                    if (!isPhoneNumberValid) {
                        userPhoneNumberError = "Ungültige Telefonnummer"
                    }
                    if (!isPasswordValid) {
                        userPasswordError = "Das Passwort muss mindestens 3 Zeichen lang sein"
                    }
                    if (!isConfirmPasswordValid) {
                        userConfirmPasswordError = "Passwort und Passwort wiederholung müssen identisch sein"
                    }
                }


            },

            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .width(250.dp)
                .padding(16.dp, 8.dp, 16.dp, 8.dp),

            ) {
            Text(text = "Registrieren")
        }
        Button(
            onClick = { vm.navigateToLogin() },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            elevation = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp, 16.dp, 8.dp),

            ) {
            Text(text = "Bereits Mitglied? zum Anmeldung", textDecoration = TextDecoration.Underline)
        }
    }
}