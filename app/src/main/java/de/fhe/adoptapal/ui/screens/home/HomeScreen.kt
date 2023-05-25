package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import de.fhe.adoptapal.data.FakeDatabase
import de.fhe.adoptapal.ui.screens.core.LocalScaffoldState
import de.fhe.adoptapal.ui.screens.core.NavigationManager
import de.fhe.adoptapal.ui.screens.core.Screen
import de.fhe.adoptapal.ui.screens.util.FullscreenPlaceholderView
import org.koin.androidx.compose.getKoin

@Composable
fun HomeScreen(vm: HomeScreenViewModel, modifier: Modifier = Modifier) {
    val animalList = vm.animalList

    Column(modifier = modifier) {

        if (animalList.isNotEmpty()) {
            AnimalList(
                animalList,
                modifier = modifier
            ) {
                vm.navigateToAnimal(it)
            }
        } else {
            FullscreenPlaceholderView("No Animals", Icons.Filled.Info)
        }
    }
}