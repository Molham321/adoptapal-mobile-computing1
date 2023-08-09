package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.ui.screens.home.filters.DistanceToCityFilter
import de.fhe.adoptapal.ui.screens.home.filters.FilterButtons
import de.fhe.adoptapal.ui.screens.home.filters.FilterDropdown
import de.fhe.adoptapal.ui.screens.home.filters.FilterTextField
import de.fhe.adoptapal.ui.screens.home.filters.GenderFilter
import de.fhe.adoptapal.ui.screens.home.filters.NumericRangeFilter

/**
 * A Composable function that displays a screen for applying filters to animal search results.
 *
 * @param vm The ViewModel associated with the SearchScreen.
 * @param modifier The modifier for styling or layout adjustments.
 * @param onFiltersApplied The callback function to be invoked when filters are applied.
 * @param onResetFilters The callback function to be invoked when filters are reset.
 */
@Composable
fun SearchScreen(
    vm: HomeScreenViewModel,
    modifier: Modifier = Modifier,
    onFiltersApplied: (List<Animal>) -> Unit,
    onResetFilters: () -> Unit
) {
    // Create a scrollable column layout for filter options
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
        // Display the header
        Text(
            text = "Tiere filtern",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = modifier.align(Alignment.CenterHorizontally)
        )

        // Display filter options

        // Name filter
        FilterTextField(
            label = "Name:",
            value = vm.initialName,
            onValueChange = { vm.initialName = it }
        )

        // Description filter
        FilterTextField(
            label = "Beschreibung:",
            value = vm.initialDescription,
            onValueChange = { vm.initialDescription = it }
        )

        // Age filter
        NumericRangeFilter(
            title = "Alter:",
            rangeFrom = vm.initialAgeFrom,
            rangeTo = vm.initialAgeTo,
            onRangeFromChange = { vm.initialAgeFrom = it },
            onRangeToChange = { vm.initialAgeTo = it }
        )

        // Weight filter
        NumericRangeFilter(
            title = "Gewicht:",
            rangeFrom = vm.initialWeightFrom,
            rangeTo = vm.initialWeightTo,
            onRangeFromChange = { vm.initialWeightFrom = it },
            onRangeToChange = { vm.initialWeightTo = it }
        )

        // Apply and Reset buttons
        FilterButtons(
            onApplyClicked = {
                val filteredAnimals = vm.updateAnimalList(
                    ageFrom = vm.initialAgeFrom,
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
                    distance = vm.initialDistance,
                    art = vm.initialArt.takeIf { it.isNotBlank() },
                    name = vm.initialName.takeIf { it.isNotBlank() },
                    description = vm.initialDescription.takeIf { it.isNotBlank() }

                )
                onFiltersApplied(filteredAnimals)
            },
            onResetClicked = {
                vm.resetFilterValues()
                onResetFilters()
            }
        // Gender filter
        GenderFilter(
            selectedGender = vm.initialSelectedGender,
            genderOptions = genderOptions,
            onGenderSelected = { vm.initialSelectedGender = it }
        )

        // City filter
        FilterTextField(
            label = "Stadt:",
            value = vm.initialCity,
            onValueChange = { vm.initialCity = it }
        )

        // CityDistance filter
        DistanceToCityFilter(
            distance = vm.initialDistance,
            onDistanceSelected = { vm.initialDistance = it },
            onClearDistance = { vm.initialDistance = 0 }
        )

        // Color filter
        FilterDropdown(
            title = "Farbe:",
            selectedValue = vm.initialColor,
            options = colors,
            onValueSelected = { vm.initialColor = it }
        )

        // Animal Category filter
        FilterDropdown(
            title = "Art:",
            selectedValue = vm.initialArt,
            options = arts,
            onValueSelected = { vm.initialArt = it }
        )

        // Apply and Reset buttons
        FilterButtons(
            onApplyClicked = {
                // Apply filters and invoke callback
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
                    distance = vm.initialDistance,
                    art = vm.initialArt.takeIf { it.isNotBlank() },
                    name = vm.initialName.takeIf { it.isNotBlank() },
                    description = vm.initialDescription.takeIf { it.isNotBlank() }
                )
                onFiltersApplied(filteredAnimals)
            },
            onResetClicked = {
                // Reset filters and invoke callback
                vm.resetFilterValues()
                onResetFilters()
            }
        )

        // Add spacer for layout spacing
        Spacer(modifier = modifier.height(120.dp))
    }
}
