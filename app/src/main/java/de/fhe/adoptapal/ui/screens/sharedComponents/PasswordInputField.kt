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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R

/**
 * A Composable function that displays a password input field with a visibility toggle button.
 *
 * @param text The current text value of the input field.
 * @param editing Whether the input field is in an editing state.
 * @param onTextChange The callback for handling text changes.
 * @param inputPlaceholder The placeholder text to display when the field is empty.
 */
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

    // Display the password input field
    OutlinedTextField(
        value = text,
        onValueChange = { newValue -> onTextChange(newValue) },
        modifier = Modifier
            .padding(8.dp),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        label = { Text(inputPlaceholder) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            // Display the visibility toggle icon button
            IconButton(
                onClick = { passwordVisible = !passwordVisible },
                modifier = Modifier
                    .padding(end = 4.dp)
            ) {
                Icon(
                    painter = painterResource(if (passwordVisible) R.drawable.ic_eye else R.drawable.ic_eye_off),
                    contentDescription = stringResource(R.string.password_visibility_toggle),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}

