package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.Location
import de.fhe.adoptapal.ui.screens.sharedComponents.GenderTag
import de.fhe.adoptapal.ui.theme.BackgroundGreyOpacity
import de.fhe.adoptapal.ui.theme.LightModeText
import org.koin.androidx.compose.koinViewModel

/**
 * A Composable function that displays a card representing an animal.
 *
 * @param animal The animal data to be displayed.
 * @param modifier The modifier for styling or layout adjustments.
 * @param userAddress The user's address for distance calculation.
 * @param onItemPressed The callback function to be invoked when the card is pressed.
 */
@Composable
fun AnimalCard(
    animal: Animal,
    modifier: Modifier = Modifier,
    userAddress: Address?,
    onItemPressed: (itemId: Long) -> Unit = {}
) {
    val vm: HomeScreenViewModel = koinViewModel()

    // Create a card layout for the animal information
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onItemPressed(animal.id) }),
        elevation = 0.dp,
        backgroundColor = BackgroundGreyOpacity,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // Display animal image

            if (animal.imageFilePath == null) {
                var image: Painter
                when (animal.animalCategory.name) {
                    "Katze" -> {
                        image = painterResource(id = R.drawable.andresllanezas_katze)
                    }

                    "Hund" -> {
                        image = painterResource(id = R.drawable.andresllanezas_hund)
                    }

                    "Fisch" -> {
                        image = painterResource(id = R.drawable.andresllanezas_fisch)
                    }

                    "Reptil" -> {
                        image = painterResource(id = R.drawable.andresllanezas_reptil)
                    }

                    "Nagetier" -> {
                        image = painterResource(id = R.drawable.andresllanezas_nagetier)
                    }

                    "Vogel" -> {
                        image = painterResource(id = R.drawable.andresllanezas_vogel)
                    }

                    else -> {
                        image = painterResource(id = R.drawable.andresllanezas_andere)
                    }
                }

                // Display default images for animal categories
                Image(
                    modifier = modifier
                        .size(80.dp, 80.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    painter = image,
                    alignment = Alignment.CenterStart,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

            } else {

                // Display image from the database
                AsyncImage(
                    model = animal.imageFilePath,
                    contentDescription = null,
                    modifier = modifier
                        .size(80.dp, 80.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = modifier.width(16.dp))

            // Display animal details
            Column(modifier = modifier.align(Alignment.CenterVertically)) {
                Text(
                    text = animal.name,
                    modifier = modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    color = LightModeText,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = modifier.height(8.dp))

                // Display age
                Text(
                    text = animal.getAge(),
                    modifier = modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    color = Color.Gray,
                )

                // Display location
                Row(verticalAlignment = Alignment.Bottom) {
                    // Display location icon
                    val location: Painter = painterResource(id = R.drawable.location)
                    Icon(
                        painter = location,
                        contentDescription = null,
                        modifier = modifier.size(16.dp, 16.dp),
                        tint = LightModeText
                    )

                    // Display city and distance
                    animal.supplier.address?.let { supplierAddress ->
                        var animalLocation = supplierAddress.city
                        var distance = ""

                        if (userAddress != null) {
                            if (userAddress.city != supplierAddress.city) {
                                distance = Location(userAddress.latitude, userAddress.longitude)
                                    .calculateDistanceTo(
                                        supplierAddress.latitude,
                                        supplierAddress.longitude
                                    )
                                    .toInt()
                                    .toString()
                                animalLocation = "${supplierAddress.city} (${distance}km)"
                            }
                        }

                        Text(
                            text = animalLocation,
                            modifier = modifier.padding(8.dp, 12.dp, 12.dp, 0.dp),
                            color = Color.Gray,
                        )
                    }
                }
            }

            // Display gender and create time difference
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                GenderTag(animal.isMale)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "vor ${animal.getCreateTimeDifference()}",
                    modifier = modifier.padding(8.dp, 0.dp, 12.dp, 12.dp),
                    color = Color.Gray,
                    style = MaterialTheme.typography.overline
                )
            }
        }
    }
}