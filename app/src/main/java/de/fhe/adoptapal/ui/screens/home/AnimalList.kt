package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.fhe.adoptapal.domain.Animal

@Composable
fun AnimalList(
    animals: List<Animal>,
    filterText: String = "",
    selectedFilter: String?,
    modifier: Modifier = Modifier,
    onItemPressed: (itemId: Long) -> Unit = {}
) {
    val filteredAnimals = animals.filter { animal ->
        animal.name.contains(filterText, ignoreCase = true) &&
                (selectedFilter == null || selectedFilter == "All" ||
                        (selectedFilter == "Male" && animal.isMale) ||
                        (selectedFilter == "Female" && !animal.isMale))
    }

    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState, modifier = modifier) {
        items(
            items = filteredAnimals,
            key = { it.id }
        ) {
            AnimalCard(
                it,
                modifier = modifier,
                onItemPressed
            )
        }
    }
}

