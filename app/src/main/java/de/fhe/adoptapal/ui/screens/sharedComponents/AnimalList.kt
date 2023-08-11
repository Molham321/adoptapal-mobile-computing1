package de.fhe.adoptapal.ui.screens.sharedComponents

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.ui.screens.home.AnimalCard

/**
 * A Composable function that displays a list of animals using a LazyColumn.
 *
 * @param animals The list of animals to be displayed.
 * @param modifier The [Modifier] to be applied to the LazyColumn.
 * @param userAddress The user's address used for calculating distances (if available).
 * @param onItemPressed The callback when an item is pressed.
 */
@Composable
fun AnimalList(
    animals: List<Animal>,
    modifier: Modifier = Modifier,
    userAddress: Address?,
    onItemPressed: (itemId: Long) -> Unit = {}
) {

    // Create a remember LazyListState to handle scroll state
    val scrollState = rememberLazyListState()

    // Create a LazyColumn to display the list of animals
    LazyColumn(state = scrollState, modifier = modifier) {
        items(
            items = animals,
            key = { it.id }
        ) {
            // Display an AnimalCard for each animal
            AnimalCard(
                it,
                modifier = modifier,
                userAddress = userAddress,
                onItemPressed
            )
        }
    }
}

