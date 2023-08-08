package de.fhe.adoptapal.ui.screens.sharedComponents

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.home.AnimalCard

@Composable
fun AnimalList(
    animals: List<Animal>,
    modifier: Modifier = Modifier,
    userAddress: Address?,
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
                userAddress = userAddress,
                onItemPressed
            )
        }
    }
}

