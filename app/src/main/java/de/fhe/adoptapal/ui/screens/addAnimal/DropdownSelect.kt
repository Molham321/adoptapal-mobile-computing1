package de.fhe.adoptapal.ui.screens.addAnimal

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue // only if using var

@Composable
fun DropdownSelect(
    dropdownCat: String = "Dropdown",
    listItems: Array<String> = arrayOf("Hund", "Katze", "Nagetier", "Reptil", "Vogel")
) {
    // val listItems = arrayOf("Hund", "Katze", "Nagetier", "Reptil", "Vogel")
    // val disabledItem = 1
    val contextForToast = LocalContext.current.applicationContext

    var expanded by remember { mutableStateOf(false) }
    // var expanded = true

    Box(
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Gray),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            onClick = {
                expanded = true
            }
        ) {
            Text(
                // text = "Tierrasse   v",
                text = dropdownCat + "   v",
                color = Color.Gray
            )
        }

        // drop down menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            // adding items
            listItems.forEachIndexed { itemIndex, itemValue ->
                DropdownMenuItem(
                    onClick = {
                        Toast.makeText(contextForToast, itemValue, Toast.LENGTH_SHORT)
                            .show()
                        expanded = false
                    },
                    // enabled = (itemIndex != disabledItem)
                ) {
                    Text(text = itemValue)
                }
            }
        }
    }
}