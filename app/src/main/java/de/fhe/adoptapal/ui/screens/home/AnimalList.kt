package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import de.fhe.adoptapal.domain.Animal

@Composable
fun AnimalList(
    animals: List<Animal>,
    filterText: String = "",
    modifier: Modifier = Modifier,
    onItemPressed: (itemId: Long) -> Unit = {}
) {
    val filteredAnimals = animals.filter { animal ->
        animal.name.contains(filterText, ignoreCase = true)
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

