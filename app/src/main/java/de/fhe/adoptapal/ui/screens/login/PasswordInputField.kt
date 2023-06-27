package de.fhe.adoptapal.ui.screens.login

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material.TextField
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

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
        shape = RoundedCornerShape(20.dp),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        // label = { Text(inputLabel) },
        placeholder = { Text(inputPlaceholder) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
    )
    val visibilityIcon: ImageVector = if (passwordVisible) Icons.Filled.Search else Icons.Filled.Done
    IconButton(
        onClick = { passwordVisible = !passwordVisible },
        modifier = Modifier.padding(start = 4.dp)
    ) {
        Icon(imageVector = visibilityIcon, contentDescription = "Password Visibility Toggle")
    }
}
