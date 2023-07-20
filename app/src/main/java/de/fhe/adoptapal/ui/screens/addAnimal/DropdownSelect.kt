package de.fhe.adoptapal.ui.screens.addAnimal

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.ui.theme.LightModeSecondary
import de.fhe.adoptapal.ui.theme.LightModeText

@Composable
fun DropdownSelect(
    dropdownCat: String = "Dropdown",
    dropdownValue: Long,
    editing: Boolean = false,
    listItems: Array<String> = arrayOf("Hund", "Katze", "Nagetier", "Reptil", "Vogel"),
    dropdownItems: Map<Long, String> = mapOf<Long, String>(),
    onValueChange: (Long) -> Unit
) {
    // val listItems = arrayOf("Hund", "Katze", "Nagetier", "Reptil", "Vogel")
    // val disabledItem = 1
    val contextForToast = LocalContext.current.applicationContext

    var expanded by remember { mutableStateOf(false) }
    // var expanded = true
    var selectedValue by remember { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                expanded = true
            }
        ) {
            if (selectedValue == "") {
                Text(
                    // text = "Tierrasse   v",
                    // text = dropdownCat + "   v",
                    text = ("* " + dropdownCat),
                    color = Color.White
                )
            } else {
                Text(
                    // text = "Tierrasse   v",
                    text = dropdownCat + ": " + dropdownItems[dropdownValue],
                    color = Color.White
                )
            }
        }

        DropdownMenu(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            dropdownItems.forEach { entry ->
                DropdownMenuItem(
                    onClick = {
                        selectedValue = entry.value
                        onValueChange(entry.key)
                        expanded = false
//                        println("ausgewählt: " + entry.value)
//                        println("richtige ID: " + entry.key)
//                        println("Tier ID: " + dropdownValue)
                    }
                ) {
                    Text(text = entry.value, color = LightModeText)
                }
            }
        }

        // drop down menu
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = {
//                expanded = false
//            }
//        ) {
//            // adding items
//            listItems.forEachIndexed { itemIndex, itemValue ->
//                DropdownMenuItem(
//                    onClick = {
//                        selectedValue = itemValue
//                        onValueChange(itemValue)
////                        Toast.makeText(contextForToast, itemValue, Toast.LENGTH_SHORT)
////                            .show()
//                        expanded = false
//                    },
//                    // enabled = (itemIndex != disabledItem)
//                ) {
//                    Text(text = itemValue)
//                }
//            }
//        }
    }
}