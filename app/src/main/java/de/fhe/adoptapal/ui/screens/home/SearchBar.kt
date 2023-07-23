package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import de.fhe.adoptapal.ui.theme.BackgroundWhite

@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "Suchen...",
    onClear: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    Row(modifier) {
        TextField(
            value = searchText,
            onValueChange = { text ->
                searchText = text
                onSearch(text)
            },
            modifier = Modifier.weight(1f),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray
                )
            },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    ClearButton(onClear = {
                        searchText = ""
                        onClear()
                    })
                }
            },
            placeholder = {
                Text(text = hint)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = BackgroundWhite,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(searchText)
                }
            )
        )
    }
}

@Composable
private fun ClearButton(onClear: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = {
            onClear()
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Clear Icon",
            tint = Color.Gray
        )
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(onSearch = {}, hint = "Suchen...", onClear = {})
}

@Preview
@Composable
private fun ClearButtonPreview() {
    ClearButton(onClear = {})
}