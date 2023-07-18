package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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

    vm.refreshUser()

    fun clearFilter() {
        filterText = ""
        selectedFilter = null
    }

    Column(modifier = modifier) {

        SearchBar(
            onSearch = { text -> filterText = text },
            onClear = { clearFilter() },
            modifier = Modifier.fillMaxWidth()
        )

        val filters = listOf("All", "Male", "Female")

        FilterBar(filters = filters, selectedFilter = selectedFilter) { filter ->
            selectedFilter = filter
        }

        if (animalList.value.isNotEmpty()) {

            val filteredAnimals = vm.getFilteredAnimals(filterText, selectedFilter)

            AnimalList(
                animals = filteredAnimals,
                modifier = modifier
            ) {
                vm.navigateToAnimal(it)
            }
        } else {
            FullscreenPlaceholderView("No Animals", Icons.Filled.Info)
        }
    }
}
