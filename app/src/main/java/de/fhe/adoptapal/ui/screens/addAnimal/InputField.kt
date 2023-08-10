package de.fhe.adoptapal.ui.screens.addAnimal

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R

@Composable
fun InputField(
    text: TextFieldValue,
    editing: Boolean = false,
    labelValue: String = "Name",
    onTextChange: (TextFieldValue) -> Unit
) {
    val focusManager = LocalFocusManager.current

    if (!editing) focusManager.clearFocus()

    when (labelValue) {
        stringResource(R.string.weight) -> {
            OutlinedTextField(
                value = text,
                onValueChange = { newValue -> onTextChange(newValue) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Decimal
                ),
                label = { Text("* $labelValue in kg") },
                placeholder = { Text("") },
            )
        }
        stringResource(id = R.string.description_of_the_animal) -> {
            OutlinedTextField(
                value = text,
                onValueChange = { newValue -> onTextChange(newValue) },
                modifier = Modifier
                    .height(200.dp)
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                label = { Text("* $labelValue") },
                placeholder = { Text("") },
            )
        }
        else -> {
            OutlinedTextField(
                value = text,
                onValueChange = { newValue -> onTextChange(newValue) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                label = { Text("* $labelValue") },
                placeholder = { Text("") },
            )
        }
    }
}
