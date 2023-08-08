package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
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

    vm.refreshUser()

    fun clearFilter() {
        filterText = ""
    }

    Column(modifier = modifier) {

        if (vm.showFilterDialog) {
            AlertDialog(
                onDismissRequest = { vm.showFilterDialog = false },
//                title = { Text(text = "Filteroptionen") },
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

        if (animalList.value.isNotEmpty()) {

            var filteredAnimals = vm.getFilteredAnimals(vm.filteredAnimals.value, filterText)

            if (filteredAnimals.isEmpty()) {
                filteredAnimals = vm.getFilteredAnimals(vm.animalList.value, filterText)
            }

            AnimalList(
                animals = filteredAnimals,
                modifier = modifier,
                userAddress = vm.user.value?.address
            ) {
                vm.navigateToAnimal(it)
            }
        } else {
            FullscreenPlaceholderView("Keine Tiere", Icons.Filled.Info)
        }
    }
}

