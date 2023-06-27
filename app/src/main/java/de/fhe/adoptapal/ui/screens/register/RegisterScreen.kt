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
import org.koin.androidx.compose.getViewModel

import de.fhe.adoptapal.ui.screens.sharedComponents.InputField

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
            .background(color = colorResource(id = R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
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
            onTextChange = {newValue -> userNameTextFieldValue = newValue},
            inputPlaceholder = "User Name"
        )
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
            onTextChange = {newValue -> userEmailTextFieldValue = newValue},
            inputPlaceholder = "User Email"
        )
        InputField(
            text = userPhoneNumberTextFieldValue,
            editing = true,
            onTextChange = {newValue -> userPhoneNumberTextFieldValue = newValue},
            inputPlaceholder = "User Phone Number"
        )

        PasswordInputField(
            text = userPasswordTextFieldValue,
            editing = true,
            onTextChange = {newValue -> userPasswordTextFieldValue = newValue},
            inputPlaceholder = "User Passwort"
        )
        PasswordInputField(
            text = userConfirmPasswordTextFieldValue,
            editing = true,
            onTextChange = {newValue -> userConfirmPasswordTextFieldValue = newValue},
            inputPlaceholder = "User Confirm Password"
        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                vm.addUser(userNameTextFieldValue.text, userEmailTextFieldValue.text, userPhoneNumberTextFieldValue.text)
                // Clear Form
                userNameTextFieldValue = TextFieldValue("")
                userEmailTextFieldValue = TextFieldValue("")
                userPhoneNumberTextFieldValue = TextFieldValue("")
                userPasswordTextFieldValue = TextFieldValue("")
                userConfirmPasswordTextFieldValue = TextFieldValue("")
                // To hide keyboard
                editingState = false
              },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Green
            ),
            modifier = Modifier
                .width(250.dp)
                .padding(16.dp, 8.dp, 16.dp, 8.dp),

            ) {
            Text(text = "Registrieren")
        }
        Button(
            onClick = {vm.navigateToLogin()},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            elevation = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp, 16.dp, 8.dp),

            ) {
            Text(text = "Bereits Mitglied? zum Login", textDecoration = TextDecoration.Underline)
        }
    }
}