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
    modifier: Modifier = Modifier,
    onItemPressed: (itemId: Long) -> Unit = {}
) {

    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState, modifier = modifier) {
        items(
            items = animals,
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

