package de.fhe.adoptapal.ui.screens.userDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.ui.screens.sharedComponents.AnimalList
import de.fhe.adoptapal.ui.screens.util.FullscreenPlaceholderView

@Composable
fun UserDetailScreen(vm: UserDetailScreenViewModel, modifier: Modifier = Modifier) {
    val animalList = remember { vm.animalList }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp, 16.dp, 16.dp, 16.dp)
    ) {

        if (vm.user.value != null) {
            UserInfo(
                user = vm.user.value!!,
                modifier = modifier
            )
        }

        if (animalList.value.isNotEmpty()) {

            Text(text = "Hochgeladene Tiere",
                modifier = Modifier.padding(16.dp, 0.dp, 12.dp, 0.dp),
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5,
                fontSize = 20.sp)

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