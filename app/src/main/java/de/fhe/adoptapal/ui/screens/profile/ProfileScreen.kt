package de.fhe.adoptapal.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.ui.screens.sharedComponents.UserDetailsAndAnimalCards

@Composable
fun ProfileScreen(vm: ProfileScreenViewModel, modifier: Modifier = Modifier) {

    vm.reload()

    val user = remember { vm.user }
    val animalList = remember { vm.animalList }

    Column {
        Text(
            text = vm.user.value?.name?.uppercase() ?: "Nicht angemeldet",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            color = colorResource(id = R.color.black),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h1,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )

        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            item {
                UserDetailsAndAnimalCards(
                    user.value,
                    animalList.value,
                    onAnimalClick = { animalId ->
                        vm.navigateToAnimal(animalId)
                    }
                )
            }
        }
    }
}