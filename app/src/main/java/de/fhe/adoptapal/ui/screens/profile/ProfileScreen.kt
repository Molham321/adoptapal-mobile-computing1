package de.fhe.adoptapal.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.ui.screens.sharedComponents.AnimalList
import de.fhe.adoptapal.ui.screens.sharedComponents.UserInfo
import de.fhe.adoptapal.ui.screens.util.FullscreenPlaceholderView

@Composable
fun ProfileScreen(vm: ProfileScreenViewModel, modifier: Modifier = Modifier) {

    vm.reload()

    val user = remember { vm.user }
    val animalList = remember { vm.animalList }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
            .padding(16.dp, 16.dp, 16.dp, 16.dp)
    ) {

        if (user.value != null) {
            UserInfo(
                user = vm.user.value!!,
                modifier = modifier
            )
        }

        if (animalList.value.isNotEmpty()) {

            AnimalList(
                animals = animalList.value,
                modifier = modifier
            ) {
                vm.navigateToAnimal(it)
            }
        } else {
            FullscreenPlaceholderView("No Animals", Icons.Filled.Info)
        }
    }
}