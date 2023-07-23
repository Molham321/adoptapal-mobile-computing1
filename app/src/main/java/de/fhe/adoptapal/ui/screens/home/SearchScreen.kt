package de.fhe.adoptapal.ui.screens.home

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.domain.Animal
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

    // Define a list of gender options
    val genderOptions = listOf("Männlich", "Weiblich", "Alle")

    // Find the index of the selected gender in the list
    var selectedGenderIndex = genderOptions.indexOfFirst {
        it.equals(
            vm.initialSelectedGender?.toString(),
            ignoreCase = true
        )
    }

    // If the isMale property is null (representing "Alle"), set the index to 2 (for "Alle" option)
    if (selectedGenderIndex == -1) {
        selectedGenderIndex = 2
    }

    // Create a separate variable to hold the selected gender
    var selectedGender by remember { mutableStateOf(genderOptions[selectedGenderIndex]) }

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
                text = "Tiere filtern",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

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
                onColorSelected = { vm.initialColor = it },
                onClearColor = { vm.initialColor = "" })

            // Weight filter
            WeightFilter(
                vm.initialWeightFrom,
                vm.initialWeightTo,
                onWeightFromChange = { vm.initialWeightFrom = it },
                onWeightToChange = { vm.initialWeightTo = it })

            // City filter
            CityFilter(vm.initialCity, onCityChange = { vm.initialCity = it })

            // Breed filter
            BreedFilter(vm.initialBreed, onBreedChange = { vm.initialBreed = it })

            // Art filte
            ArtFilter(vm.initialArt, onArtChange = { vm.initialArt = it })

            // Apply and Reset buttons
            FilterButtons(
                onApplyClicked = {
                    val filteredAnimals = vm.updateAnimalList(
                        ageFrom = vm.initialAgeFrom.takeIf { it > 0 },
                        ageTo = vm.initialAgeTo,
                        isMale = when (vm.initialSelectedGender) {
                            "Männlich" -> true
                            "Weiblich" -> false
                            else -> null // If "Alle" is selected, set to null to display all genders
                        },
                        color = vm.initialColor.takeIf { it.isNotBlank() },
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

            Spacer(modifier = Modifier.height(120.dp))
        }
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
            // Use RadioButton to represent each gender option
            RadioButton(
                selected = gender == selectedGender,
                onClick = { onGenderSelected(gender) },
                modifier = Modifier.weight(1f)
            )
            Text(text = gender)
        }
    }
}

@Composable
private fun ColorFilter(
    color: String,
    colors: List<AnimalColor>,
    onColorSelected: (String) -> Unit,
    onClearColor: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Text(text = "Farbe:")
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.align(Alignment.TopEnd),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray)
        ) {
            Text(
                text = if (color.isNotBlank()) "Wählen" else "Wählen",
                style = TextStyle(fontSize = 14.sp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            colors.forEach { colorOption ->
                DropdownMenuItem(onClick = {
                    onColorSelected(colorOption.name)
                    expanded = false // Collapse the dropdown menu after selection
                }) {
                    Text(text = colorOption.name)
                }
            }
        }
    }
    // Anzeigen der ausgewählten Farbe
    if (color.isNotBlank()) {
        Text(text = "Ausgewählte Farbe: $color")
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
    Text(text = "Standort:")
    OutlinedTextField(
        value = city,
        onValueChange = { onCityChange(it) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun BreedFilter(breed: TextFieldValue, onBreedChange: (TextFieldValue) -> Unit) {
    Text(text = "Züchten:")
    OutlinedTextField(
        value = breed,
        onValueChange = { onBreedChange(it) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun ArtFilter(art: TextFieldValue, onArtChange: (TextFieldValue) -> Unit) {
    Text(text = "Art:")
    OutlinedTextField(
        value = art,
        onValueChange = { onArtChange(it) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun FilterButtons(onApplyClicked: () -> Unit, onResetClicked: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { onApplyClicked() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text(
                text = "Anwenden",
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.onSecondary
            )
        }
        Button(
            onClick = { onResetClicked() },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Icon(
                Icons.Default.Clear,
                contentDescription = "Clear All",
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                text = "Zurücksetzen",
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.onSecondary
            )
        }
    }
}
