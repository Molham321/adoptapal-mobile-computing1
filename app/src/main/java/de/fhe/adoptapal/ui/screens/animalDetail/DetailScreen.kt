package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailScreen(vm: DetailScreenViewModel, modifier: Modifier = Modifier) {

    Column(modifier = modifier) {

        if(vm.animal.value != null) {
            Details(
                animal = vm.animal.value!! ,
                modifier = modifier
            )
        }
    }
}