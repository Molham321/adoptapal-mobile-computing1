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

/**
 * A Composable function that represents the main screen of the application's home page.
 *
 * @param vm The ViewModel associated with the HomeScreen.
 * @param modifier The modifier for styling or layout adjustments.
 */
@Composable
fun HomeScreen(vm: HomeScreenViewModel, modifier: Modifier = Modifier) {
    // Refresh the user data when the screen is composed
    vm.refreshUser()

    Column(modifier = modifier) {

        // Display the filter dialog if necessary
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

        // Display the AnimalList or placeholder view based on filtered animals
        if (vm.filteredAnimals.value.isNotEmpty()) {

            // Display the list of filtered animals
            AnimalList(
                userAddress = vm.user.value?.address,
                animals = vm.filteredAnimals.value,
                modifier = modifier
            ) {
                // Navigate to the selected animal's details screen
                vm.navigateToAnimal(it)
            }
        } else {
            // Display a placeholder view when no animals are available
            FullscreenPlaceholderView("Keine Tiere", Icons.Filled.Info)
        }
    }
}
