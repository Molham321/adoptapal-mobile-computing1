package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import de.fhe.adoptapal.ui.screens.util.FullscreenPlaceholderView

@Composable
fun HomeScreen(vm: HomeScreenViewModel, modifier: Modifier = Modifier) {
    val animalList = remember { vm.animalList }

    Column(modifier = modifier) {

        if (animalList.value.isNotEmpty()) {

            SearchBar(onSearch = {})

            val filters = listOf("Filter 1", "Filter 2", "Filter 3")
            var selectedFilter by remember { mutableStateOf<String?>(null) }

            FilterBar(filters = filters, selectedFilter = selectedFilter) { filter ->
                selectedFilter = filter
            }

            AnimalList(
                animalList.value,
                modifier = modifier
            ) {
                vm.navigateToAnimal(it)
            }
        } else {
            FullscreenPlaceholderView("No Animals", Icons.Filled.Info)
        }
    }
}