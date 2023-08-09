package de.fhe.adoptapal.ui.screens.sharedComponents


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Composable function to display an input field with options for customization.
 *
 * @param text The current text value of the input field.
 * @param onTextChange The callback to be invoked when the text value changes.
 * @param inputPlaceholder The placeholder text for the input field.
 * @param editing Flag indicating whether the input field is being edited.
 * @param modifier Modifier to be applied to the input field.
 */
@Composable
fun InputField(
    text: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    inputPlaceholder: String = "",
    editing: Boolean = false,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    // Clear focus if not in editing mode
    if (!editing) focusManager.clearFocus()

    // Create the OutlinedTextField with specified options
    OutlinedTextField(
        value = text,
        onValueChange = { newValue -> onTextChange(newValue) },
        modifier = modifier.padding(8.dp),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        label = { Text(inputPlaceholder) },
    )
}

@Preview
@Composable
fun InputFieldPreview() {
    val inputValue = remember { mutableStateOf(TextFieldValue()) }
    InputField(
        text = inputValue.value,
        onTextChange = { inputValue.value = it },
        inputPlaceholder = "Enter text...",
        editing = true
    )
}
