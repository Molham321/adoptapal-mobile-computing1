package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.ui.screens.sharedComponents.AnimalImage
import de.fhe.adoptapal.ui.screens.sharedComponents.Title

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
    modifier: Modifier = Modifier,
    onItemPressed: (itemId: Long) -> Unit = {},

    ) {

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
                text = "${animal.description}",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                color = colorResource(id = R.color.text),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Start
            )
        }

        item {

            Spacer(modifier = modifier.height(24.dp))
            Text(
                // text = "Rasse: ${"Hunde Breed"/*animal.breed*/}",
                text = "Tierart: ${animal.animalCategory.name}",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                color = colorResource(id = R.color.text),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = modifier.height(24.dp))
            Text(
                text = "Geburtstag: ${animal.birthday.dayOfMonth}.${animal.birthday.monthValue}.${animal.birthday.year}",
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoCard(title = stringResource(id = R.string.old), value = animal.getAge())
                InfoCard(title = stringResource(id = R.string.color), value = animal.color.name)
                InfoCard(title = stringResource(id = R.string.weight), value = animal.weight.toString().plus("Kg"))
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
