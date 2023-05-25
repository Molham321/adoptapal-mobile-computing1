package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.fhe.adoptapal.ui.screens.home.AnimalList
import de.fhe.adoptapal.ui.screens.util.FullscreenPlaceholderView

@Composable
fun DetailScreen(vm: DetailScreenViewModel, modifier: Modifier = Modifier ) {
    Column(modifier = modifier) {
        Details(
            animalId = vm.animalId,
            modifier = modifier
        )
    }
}