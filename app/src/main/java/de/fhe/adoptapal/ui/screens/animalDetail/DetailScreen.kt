package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Composable function representing the detail screen for an animal.
 *
 * @param vm ViewModel for the detail screen.
 * @param modifier Modifier for the layout.
 */
@Composable
fun DetailScreen(vm: DetailScreenViewModel, modifier: Modifier = Modifier) {

    Column(modifier = modifier) {

        // Display details of the animal if available
        vm.animal.value?.let { animal ->
            Details(
                animal = animal,
                modifier = modifier
            ) { userId ->
                vm.navigateToUser(userId)
            }
        }
    }
}