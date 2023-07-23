package de.fhe.adoptapal.ui.screens.sharedComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R

@Composable
fun PasswordInputField(
    text: TextFieldValue,
    editing: Boolean = false,
    onTextChange: (TextFieldValue) -> Unit,
    inputPlaceholder: String = ""
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    if (!editing) focusManager.clearFocus()

    OutlinedTextField(
        value = text,
        onValueChange = { newValue -> onTextChange(newValue) },
        modifier = Modifier
            .padding(8.dp),
        // shape = RoundedCornerShape(20.dp),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        label = { Text(inputPlaceholder) },
        // placeholder = { Text(inputPlaceholder) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible = !passwordVisible },
                modifier = Modifier
                    .padding(end = 4.dp) // Adds padding to the end (right) of the IconButton
            ) {
                Icon(
                    painter = painterResource(if (passwordVisible) R.drawable.ic_eye else R.drawable.ic_eye_off),
                    contentDescription = "Password Visibility Toggle",
                    modifier = Modifier.size(24.dp) // Adjusts the size of the Icon
                )
            }
        }
    )
}

