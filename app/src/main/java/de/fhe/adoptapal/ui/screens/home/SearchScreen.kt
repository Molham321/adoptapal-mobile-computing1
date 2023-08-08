package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.domain.Color as AnimalColor

@Composable
fun SearchScreen(
    vm: HomeScreenViewModel,
    modifier: Modifier = Modifier,
    onFiltersApplied: (List<Animal>) -> Unit,
    onResetFilters: () -> Unit
) {
    val scrollState = rememberScrollState()
    val colors = vm.animalColorList.value
    val arts = vm.animalCategoryList.value
    val genderOptions = listOf("Männlich", "Weiblich", "Alle")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
    ) {
        Text(
            text = "Tiere filtern",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = modifier.align(Alignment.CenterHorizontally)
        )

        // Name filter
        NameFilter(vm.initialName, onNameChange = { vm.initialName = it })

        // Description filter
        DescriptionFilter(
            vm.initialDescription,
            onDescriptionChange = { vm.initialDescription = it })

        // Age filter
        AgeFilter(
            vm.initialAgeFrom,
            vm.initialAgeTo,
            onAgeFromChange = { vm.initialAgeFrom = it },
            onAgeToChange = { vm.initialAgeTo = it })

        // Gender filter
        GenderFilter(
            vm.initialSelectedGender,
            genderOptions,
            onGenderSelected = { vm.initialSelectedGender = it })

        // Color filter
        ColorFilter(
            vm.initialColor,
            colors,
            onColorSelected = { vm.initialColor = it }
        )

        // Art filte
        ArtFilter(
            vm.initialArt,
            arts,
            onArtSelected = { vm.initialArt = it }
        )

        // Weight filter
        WeightFilter(
            vm.initialWeightFrom,
            vm.initialWeightTo,
            onWeightFromChange = { vm.initialWeightFrom = it },
            onWeightToChange = { vm.initialWeightTo = it })

        // City filter
        CityFilter(vm.initialCity, onCityChange = { vm.initialCity = it })

        // Apply and Reset buttons
        FilterButtons(
            onApplyClicked = {
                val filteredAnimals = vm.updateAnimalList(
                    name = vm.initialName.takeIf { it.isNotBlank() },
                    description = vm.initialDescription.takeIf { it.isNotBlank() },
                    ageFrom = vm.initialAgeFrom.takeIf { it > 0 },
                    ageTo = vm.initialAgeTo,
                    isMale = when (vm.initialSelectedGender) {
                        "Männlich" -> true
                        "Weiblich" -> false
                        else -> null // If "Alle" is selected, set to null to display all genders
                    },
                    color = vm.initialColor.takeIf { it.isNotBlank() },
                    art = vm.initialArt.takeIf { it.isNotBlank() },
                    weightFrom = vm.initialWeightFrom,
                    weightTo = vm.initialWeightTo,
                    city = vm.initialCity.takeIf { it.isNotBlank() },
                )
                onFiltersApplied(filteredAnimals)
            },
            onResetClicked = {
                vm.resetFilterValues()
                onResetFilters()
            }
        )

        Spacer(modifier = modifier.height(120.dp))
    }
}

@Composable
private fun AgeFilter(
    ageFrom: Int,
    ageTo: Int,
    onAgeFromChange: (Int) -> Unit,
    onAgeToChange: (Int) -> Unit
) {
    Text(text = "Alter:")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = ageFrom.toString(),
            onValueChange = { onAgeFromChange(it.toIntOrNull() ?: 0) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
        Text(text = "bis")
        OutlinedTextField(
            value = ageTo.toString(),
            onValueChange = { onAgeToChange(it.toIntOrNull() ?: 0) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun GenderFilter(
    selectedGender: String,
    genderOptions: List<String>,
    onGenderSelected: (String) -> Unit
) {
    Text(text = "Geschlecht:")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        genderOptions.forEach { gender ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onGenderSelected(gender)
                    }
                    .background(
                        color = getGenderBackgroundColor(gender, selectedGender),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Text(
                    text = gender,
                    color = if (gender == selectedGender) Color.White else Color.Black,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
private fun getGenderBackgroundColor(gender: String, selectedGender: String): Color {
    return when (gender) {
        "Männlich" -> if (selectedGender == gender) Color.Blue else Color.Transparent
        "Weiblich" -> if (selectedGender == gender) Color.Red else Color.Transparent
        "Alle" -> if (selectedGender == gender) Color.Gray else Color.Transparent
        else -> Color.Transparent
    }
}

@Composable
private fun ArtFilter(
    art: String,
    arts: List<AnimalCategory>,
    onArtSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedArt by remember { mutableStateOf(art) }

    Button(
        onClick = { expanded = true },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        if (selectedArt.isNotBlank()) {
            Text(
                text = "Ausgewählte Art: $selectedArt",
                style = TextStyle(fontSize = 14.sp),
                color = MaterialTheme.colors.onBackground
            )
        } else {
            Text(
                text = "Art des Tieres", // Updated text here
                style = TextStyle(fontSize = 14.sp),
                color = MaterialTheme.colors.onBackground
            )
        }
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        arts.forEach { artOption ->
            DropdownMenuItem(onClick = {
                selectedArt = artOption.name
                onArtSelected(artOption.name)
                expanded = false // Collapse the dropdown menu after selection
            }) {
                Text(
                    text = artOption.name,
                    style = TextStyle(fontSize = 14.sp),
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}

@Composable
private fun ColorFilter(
    color: String,
    colors: List<AnimalColor>,
    onColorSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(color) }

    Button(
        onClick = { expanded = true },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        if (selectedColor.isNotBlank()) {
            Text(
                text = "Ausgewählte Farbe: $selectedColor",
                style = TextStyle(fontSize = 14.sp),
                color = MaterialTheme.colors.onBackground
            )
        } else {
            Text(
                text = "Farbe des Tieres", // Updated text here
                style = TextStyle(fontSize = 14.sp),
                color = MaterialTheme.colors.onBackground
            )
        }
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        colors.forEach { colorOption ->
            DropdownMenuItem(onClick = {
                selectedColor = colorOption.name
                onColorSelected(colorOption.name)
                expanded = false // Collapse the dropdown menu after selection
            }) {
                Text(
                    text = colorOption.name,
                    style = TextStyle(fontSize = 14.sp),
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}


@Composable
private fun WeightFilter(
    weightFrom: Int,
    weightTo: Int,
    onWeightFromChange: (Int) -> Unit,
    onWeightToChange: (Int) -> Unit
) {
    Text(text = "Gewicht:")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = weightFrom.toString(),
            onValueChange = { onWeightFromChange(it.toIntOrNull() ?: 0) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
        Text(text = "bis")
        OutlinedTextField(
            value = weightTo.toString(),
            onValueChange = { onWeightToChange(it.toIntOrNull() ?: 0) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun CityFilter(city: String, onCityChange: (String) -> Unit) {
    Text(text = "Stadt:")
    OutlinedTextField(
        value = city,
        onValueChange = { onCityChange(it) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun NameFilter(name: String, onNameChange: (String) -> Unit) {
    Text(text = "Name:")
    OutlinedTextField(
        value = name,
        onValueChange = { onNameChange(it) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun DescriptionFilter(description: String, onDescriptionChange: (String) -> Unit) {
    Text(text = "Beschreibung:")
    OutlinedTextField(
        value = description,
        onValueChange = { onDescriptionChange(it) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun FilterButtons(onApplyClicked: () -> Unit, onResetClicked: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onApplyClicked() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
            ) {
                Text(
                    text = "Anwenden",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onResetClicked() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text(
                    text = "Zurücksetzen",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onSecondary
                )
            }
        }
    }
}

