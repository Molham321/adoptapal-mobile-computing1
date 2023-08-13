package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.ui.screens.sharedComponents.AnimalImage
import de.fhe.adoptapal.ui.screens.sharedComponents.Title
import de.fhe.adoptapal.ui.theme.BackgroundGreyOpacity

/**
 * Composable function to display detailed information about an animal.
 *
 * @param animal Animal object to display details for.
 * @param modifier Modifier for styling.
 * @param onItemPressed Callback function when an item is pressed.
 */
@Composable
fun Details(
    animal: Animal,
    vm: DetailScreenViewModel,
    modifier: Modifier = Modifier,
    onItemPressed: (itemId: Long) -> Unit = {},

    ) {

    val animalIsFavorite = remember { mutableStateOf(animal.isFavorite) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {

        item {
            AnimalImage(
                modifier = modifier
                    .fillMaxWidth()
                    .height(346.dp),
                category = animal.animalCategory.name,
                imageFilePath = animal.imageFilePath
            )
            Spacer(modifier = modifier.height(16.dp))

            Surface(
                shape = CircleShape,
                modifier = modifier
                    .padding(6.dp)
                    .size(52.dp),
                color = BackgroundGreyOpacity
            ) {
                IconToggleButton(
                    checked = animalIsFavorite.value,
                    onCheckedChange = {
                        animalIsFavorite.value = it
                        animal.isFavorite = it
                        vm.saveAnimalAsFavorite(animal)
                    }
                ) {
                    Icon(
                        modifier = modifier.size(36.dp, 36.dp),
                        painter = if (animalIsFavorite.value) {
                            painterResource(id = R.drawable.baseline_bookmark_24)
                        } else {
                            painterResource(id = R.drawable.baseline_bookmark_border_24)
                        },
                        contentDescription = null
                    )
                }
            }

            AnimalInfoCard(
                animal.name,
                animal.isMale,
                animal.supplier.address,
                animal.getCreateTimeDifference()
            )
        }

        // My story details
        item {

            Spacer(modifier = modifier.height(24.dp))
            Title(title = stringResource(R.string.about_me))
            Spacer(modifier = modifier.height(16.dp))
            Text(
                // text = "ABOUT ${animal.name} \n" +
                //         "${animal.description}",
                text = animal.description,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                color = colorResource(id = R.color.text),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Start
            )
        }

        // Quick info
        item {
            Spacer(modifier = modifier.height(24.dp))
            Title(title = stringResource(R.string.metrics))
            Spacer(modifier = modifier.height(16.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoCard(title = stringResource(id = R.string.old), value = animal.getAge())
                InfoCard(title = stringResource(id = R.string.color), value = animal.color.name)
                InfoCard(title = stringResource(id = R.string.weight), value = animal.weight.toString().plus("Kg"))
            }

            Spacer(modifier = modifier.height(10.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(64.dp, 0.dp, 64.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoCard(title = "Tierart", value = animal.animalCategory.name)
                InfoCard(title = "Geburtstag", value = "${animal.birthday.dayOfMonth}.${animal.birthday.monthValue}.${animal.birthday.year}")
            }
        }

        // Owner info
        item {

            Spacer(modifier = modifier.height(24.dp))
            Title(title = stringResource(R.string.offerer))

            OwnerCard(
                animal,
                R.drawable.user,
                onItemPressed
            )
        }
    }
}
