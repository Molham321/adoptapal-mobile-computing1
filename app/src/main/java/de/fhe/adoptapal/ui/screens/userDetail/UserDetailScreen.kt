package de.fhe.adoptapal.ui.screens.userDetail

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.ui.screens.sharedComponents.UserDetailsAndAnimalCards

/**
 * A Composable function that displays the user detail screen.
 *
 * @param vm The ViewModel for the user detail screen.
 * @param modifier The modifier for customization.
 */
@Composable
fun UserDetailScreen(vm: UserDetailScreenViewModel, modifier: Modifier = Modifier) {
    val animalList = remember { vm.animalList }

    Column {
        // Display the user's name or a placeholder if no user is available
        Text(
            text = vm.user.value?.name?.uppercase() ?: stringResource(R.string.no_users),
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
            if (vm.user.value != null) {
                item {
                    // Display user details and a list of associated animal cards
                    UserDetailsAndAnimalCards(
                        vm.user.value,
                        animalList.value,
                        onAnimalClick = { animalId ->
                            vm.navigateToAnimal(animalId)
                        }
                    )
                }
            }
        }
    }
}