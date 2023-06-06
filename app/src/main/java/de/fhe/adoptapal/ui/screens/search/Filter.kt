package de.fhe.adoptapal.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.domain.Animal

@Composable
fun Filter(onSubmit: (Animal) -> Unit) {
    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf(TextFieldValue()) }
    var age by remember { mutableStateOf(0) }
    var isMale by remember { mutableStateOf(false) }
    var color by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf(0) }
    var city by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var houseNumber by remember { mutableStateOf("") }
    var zip by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf(TextFieldValue()) }
    var art by remember { mutableStateOf(TextFieldValue()) }

    val colors = listOf("Red", "Green", "Blue", "Yellow", "Orange")
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Filter Animals",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Name:")
            OutlinedTextField(
                value = name.text,
                onValueChange = { name = name.copy(it) },
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "Age:")
            OutlinedTextField(
                value = age.toString(),
                onValueChange = { age = it.toIntOrNull() ?: 0 },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "Gender:")
            Switch(
                checked = isMale,
                onCheckedChange = { isMale = it }
            )
            Text(text = "Color:")
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (color.isNotEmpty()) color else "Select Color")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    colors.forEach { colorOption ->
                        DropdownMenuItem(onClick = {
                            color = colorOption
                            expanded = false
                        }) {
                            Text(text = colorOption)
                        }
                    }
                }
            }
            Text(text = "Weight:")
            OutlinedTextField(
                value = weight.toString(),
                onValueChange = { weight = it.toIntOrNull() ?: 0 },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "Location:")
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                modifier = Modifier.fillMaxWidth()
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = street,
                    onValueChange = { street = it },
                    modifier = Modifier.weight(2f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = houseNumber,
                    onValueChange = { houseNumber = it },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = zip,
                    onValueChange = { zip = it },
                    modifier = Modifier.weight(1f)
                )
            }
            Text(text = "Breed:")
            OutlinedTextField(
                value = breed.text,
                onValueChange = { breed = breed.copy(it) },
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "Art:")
            OutlinedTextField(
                value = art.text,
                onValueChange = { art = art.copy(it) },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Submit")
            }
        }
    }
}

@Preview
@Composable
fun PreviewJetpackFilter() {
    Filter(onSubmit = {})
}
