package de.fhe.adoptapal.ui.screens.home.filters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun DistanceToCityFilter(
    distance: Int,
    onDistanceSelected: (Int) -> Unit,
    onClearDistance: () -> Unit
) {
    val distances = listOf(0, 5, 10, 20, 50)
    var expanded by remember { mutableStateOf(false) }

    Text(text = "Distanz:")
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.align(Alignment.TopEnd),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray)
        ) {
            Text(
                text = "Auswählen",
                style = TextStyle(fontSize = 14.sp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            distances.forEach { distanceOption ->
                DropdownMenuItem(onClick = {
                    onDistanceSelected(distanceOption)
                    expanded = false // Collapse the dropdown menu after selection
                }) {
                    Text(text = "$distanceOption km")
                }
            }
        }
    }
    // Anzeigen der ausgewählten Farbe
    if (distance == 0) {
        Text(text = "Alle Orte")
    } else {
        Text(text = "Alle Orte im Umkreis von $distance km")
    }
}