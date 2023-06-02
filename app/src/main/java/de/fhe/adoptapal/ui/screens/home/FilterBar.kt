package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun FilterBar(
    filters: List<String>,
    selectedFilter: String?,
    onFilterSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row{
        Box {
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp)
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
                modifier = Modifier
                    .widthIn(max = 240.dp)
                    .background(MaterialTheme.colors.surface)
                    .padding(8.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .shadow(4.dp)
            ) {
                DropdownMenuItem(
                    onClick = {
                        onFilterSelected(null.toString())
                        expanded = false
                    }
                ) {
                    Text(text = "All")
                }
                filters.forEach { filter ->
                    DropdownMenuItem(
                        onClick = {
                            onFilterSelected(filter)
                            expanded = false
                        }
                    ) {
                        Text(text = filter)
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