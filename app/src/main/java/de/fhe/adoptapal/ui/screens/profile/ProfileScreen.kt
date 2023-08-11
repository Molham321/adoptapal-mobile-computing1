package de.fhe.adoptapal.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.ui.screens.home.AnimalCard
import de.fhe.adoptapal.ui.screens.util.FullscreenPlaceholderView

@Composable
fun ProfileScreen(vm: ProfileScreenViewModel, modifier: Modifier = Modifier) {

    vm.reload()

    val user = remember { vm.user }
    val favoriteAnimalList = remember { vm.favoriteAnimalList }
    val userAnimalList = remember { vm.userAnimalList }
    val listState = remember { mutableStateOf(false) }

    Column {
        Text(
            text = vm.user.value?.name?.uppercase() ?: stringResource(R.string.not_logged_in),
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
            modifier = modifier
                .fillMaxSize()
        ) {

            if (user.value != null) {
                item {
                    Column(modifier = modifier) {

                        Spacer(modifier = modifier.height(24.dp))
                        Text(
                            text = "${stringResource(id = R.string.email)}: ${vm.user.value!!.email}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp, 16.dp, 0.dp),
                            color = colorResource(id = R.color.black),
                            style = MaterialTheme.typography.overline,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = modifier.height(24.dp))
                        Text(
                            text = "${stringResource(id = R.string.phone)}: ${vm.user.value!!.phoneNumber}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp, 16.dp, 0.dp),
                            color = colorResource(id = R.color.black),
                            style = MaterialTheme.typography.overline,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = modifier.height(24.dp))
                    }
                }
            }

            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    if (user.value != null) {
                        Switch(
                            modifier = Modifier
                                .width(50.dp),
                            checked = listState.value,
                            onCheckedChange = {
                                listState.value = it
                            })
                    }
                    if(!listState.value) {
                        Text(
                            text = stringResource(R.string.marked_animals),
                            modifier = Modifier.padding(16.dp, 0.dp, 12.dp, 0.dp),
                            color = colorResource(id = R.color.black),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.h5,
                            fontSize = 20.sp
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.uploaded_animals),
                            modifier = Modifier.padding(16.dp, 0.dp, 12.dp, 0.dp),
                            color = colorResource(id = R.color.black),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.h5,
                            fontSize = 20.sp
                        )
                    }
                }
            }

            if(!listState.value) {
                if (favoriteAnimalList.value.isNotEmpty()) {
                    item {
                    }
                    items(
                        items = favoriteAnimalList.value,
                        key = { it.id }
                    ) { it ->
                        AnimalCard(
                            it,
                            modifier = modifier,
                            userAddress = vm.user.value?.address
                        ) {
                            vm.navigateToAnimal(it)
                        }
                    }
                } else {
                    item {
                        FullscreenPlaceholderView(stringResource(R.string.no_animals_marked), Icons.Filled.Info)
                    }
                }
            } else {
                if (userAnimalList.value.isNotEmpty()) {
                    item {
                    }
                    items(
                        items = userAnimalList.value,
                        key = { it.id }
                    ) { it ->
                        AnimalCard(
                            it,
                            modifier = modifier,
                            userAddress = vm.user.value?.address,
                        ) {
                            vm.navigateToAnimal(it)
                        }
                    }
                } else {
                    item {
                        FullscreenPlaceholderView(stringResource(id = R.string.no_animals_uploaded), Icons.Filled.Info)
                    }
                }
            }
        }
    }
}