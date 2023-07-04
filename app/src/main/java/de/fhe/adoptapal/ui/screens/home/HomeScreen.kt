package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import de.fhe.adoptapal.ui.screens.util.FullscreenPlaceholderView

@Composable
fun HomeScreen(vm: HomeScreenViewModel, modifier: Modifier = Modifier) {
    val animalList = remember { vm.animalList }
    var filterText by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf<String?>(null) }

    vm.refreshUser()

    Column(modifier = modifier) {

        if (animalList.value.isNotEmpty()) {

            SearchBar(onSearch = { text -> filterText = text })

            val filters = listOf("All", "Male", "Female")

            FilterBar(filters = filters, selectedFilter = selectedFilter) { filter ->
                selectedFilter = filter
            }

            AnimalList(
                animals = animalList.value,
                filterText = filterText,
                selectedFilter = selectedFilter,
                modifier = modifier
            ) {
                vm.navigateToAnimal(it)
            }
        } else {
            FullscreenPlaceholderView("No Animals", Icons.Filled.Info)
        }
    }
}
