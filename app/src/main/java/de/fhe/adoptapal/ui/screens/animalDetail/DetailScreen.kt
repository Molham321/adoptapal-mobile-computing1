package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailScreen(vm: DetailScreenViewModel, modifier: Modifier = Modifier) {

    val animal = vm.animal
    Column(modifier = modifier) {

        Details(
            animal = animal!! ,
            modifier = modifier
        )
    }
}