package de.fhe.adoptapal.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.ui.screens.home.AnimalCard
import de.fhe.adoptapal.ui.screens.userDetail.RatingBar
import de.fhe.adoptapal.ui.screens.util.FullscreenPlaceholderView

@Composable
fun ProfileScreen(vm: ProfileScreenViewModel, modifier: Modifier = Modifier) {

    vm.reload()

    val user = remember { vm.user }
    val animalList = remember { vm.animalList }

    Column {
        Text(
            text = vm.user.value?.name?.uppercase() ?: "Keine User!",
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp, 0.dp, 0.dp)
                        ) {
                            RatingBar(rating = 4.0)
                        }

                        Spacer(modifier = modifier.height(24.dp))
                        Text(
                            text = "Email: ${vm.user.value!!.email}",
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
                            text = "Telefon: ${vm.user.value!!.phoneNumber}",
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

            if (animalList.value.isNotEmpty()) {
                item {
                    Text(
                        text = "Gemerkte Tiere",
                        modifier = Modifier.padding(16.dp, 0.dp, 12.dp, 0.dp),
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h5,
                        fontSize = 20.sp
                    )
                }
                items(
                    items = animalList.value,
                    key = { it.id }
                ) {
                    AnimalCard(
                        it,
                        modifier = modifier,
                    ) {
                        vm.navigateToAnimal(it)
                    }
                }
            } else {
                item {
                    FullscreenPlaceholderView("No Animals", Icons.Filled.Info)
                }
            }
        }
    }
}