package de.fhe.adoptapal.ui.screens.home

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.Location
import de.fhe.adoptapal.ui.screens.sharedComponents.AnimalImage
import de.fhe.adoptapal.ui.screens.sharedComponents.GenderTag
import de.fhe.adoptapal.ui.theme.BackgroundGreyOpacity
import de.fhe.adoptapal.ui.theme.LightModeText

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
            AnimalImage(
                modifier = modifier,
                category = animal.animalCategory.name,
                imageFilePath = animal.imageFilePath
            )
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