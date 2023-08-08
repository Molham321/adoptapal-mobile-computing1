package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
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
    vm.refreshUser()

    Column(modifier = modifier) {

        if (vm.showFilterDialog) {
            AlertDialog(
                onDismissRequest = { vm.showFilterDialog = false },
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

        if (vm.filteredAnimals.value.isNotEmpty()) {

            AnimalList(
                userAddress = vm.user.value?.address,
                animals = vm.filteredAnimals.value,
                modifier = modifier
            ) {
                vm.navigateToAnimal(it)
            }
        } else {
            FullscreenPlaceholderView("Keine Tiere", Icons.Filled.Info)
        }
    }
}

