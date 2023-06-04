package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.fhe.adoptapal.model.Animal

@Composable
fun AnimalList(
    animals: List<Animal>,
    modifier: Modifier = Modifier,
    onItemPressed: (itemId: Int) -> Unit = {}
) {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState, modifier = modifier) {
        items(
            items = animals,
            key = { it.id }
        ) {
            AnimalCard(it, modifier = modifier, onItemPressed)
        }
    }
}