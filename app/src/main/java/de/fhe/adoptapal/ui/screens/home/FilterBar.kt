package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.ui.theme.LightModeSecondary
import de.fhe.adoptapal.ui.theme.LightModeText


@Composable
fun FilterBar(
    filters: List<String>,
    selectedFilter: String?,
    onFilterSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row {
        Box {
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(text = selectedFilter ?: "All")
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
//                modifier = Modifier
//                    .widthIn(max = 240.dp)
//                    .background(MaterialTheme.colors.surface)
//                    .padding(8.dp)
//                    .clip(shape = MaterialTheme.shapes.medium)
//                    .shadow(4.dp)
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                filters.forEach { filter ->
                    DropdownMenuItem(
                        onClick = {
                            onFilterSelected(filter)
                            expanded = false
                        }
                    ) {
                        Text(text = filter, color = LightModeText)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewFilterBar() {
    val filters = listOf("Filter 1", "Filter 2", "Filter 3")
    var selectedFilter by remember { mutableStateOf<String?>(null) }

    FilterBar(filters = filters, selectedFilter = selectedFilter) { filter ->
        selectedFilter = filter
    }
}