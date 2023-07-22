package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import de.fhe.adoptapal.ui.screens.sharedComponents.AnimalList
import de.fhe.adoptapal.ui.screens.util.FullscreenPlaceholderView

@Composable
fun HomeScreen(vm: HomeScreenViewModel, modifier: Modifier = Modifier) {
    val animalList = remember { vm.animalList }
    var filterText by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf<String?>(null) }

    val filters = listOf("Alle", "Männlich", "Weiblich")

    vm.refreshUser()

    fun clearFilter() {
        filterText = ""
        selectedFilter = null
    }

    Column(modifier = modifier) {

        // The AlertDialog for filter options
        if (vm.showFilterDialog) {
            AlertDialog(
                onDismissRequest = { vm.showFilterDialog = false },
                title = { Text(text = "Filter Options") },
                text = {
                    // Call the SearchScreen composable to display the filter options
                    SearchScreen(
                        vm = vm,
                        onFiltersApplied = { filteredAnimals ->
                            vm.updateFilteredAnimalList(filteredAnimals)
                            vm.showFilterDialog = false
                        }
                    ) {
                        // Reset the filters and show all animals
                        vm.resetFiltersAndShowAllAnimals()
                        vm.showFilterDialog = false // Close the dialog after resetting filters
                    }
                },
                confirmButton = {}
            )
        }

        SearchBar(
            onSearch = { text -> filterText = text },
            onClear = { clearFilter() },
            modifier = Modifier.fillMaxWidth()
        )

        FilterBar(filters = filters, selectedFilter = selectedFilter) { filter ->
            selectedFilter = filter
        }

        if (animalList.value.isNotEmpty()) {

            var filteredAnimals = vm.getFilteredAnimals(vm.filteredAnimals.value, filterText, selectedFilter)

            if (filteredAnimals.isEmpty()) {
                filteredAnimals = vm.animalList.value
            }

            AnimalList(
                animals = filteredAnimals,
                modifier = modifier
            ) {
                vm.navigateToAnimal(it)
            }
        } else {
            FullscreenPlaceholderView("Keine Tiere", Icons.Filled.Info)
        }
    }
}

