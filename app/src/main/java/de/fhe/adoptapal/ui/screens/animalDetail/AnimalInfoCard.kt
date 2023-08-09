package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.ui.screens.sharedComponents.GenderTag

/**
 * Composable function to display information about an animal's name, gender, location, and creation time.
 *
 * @param name The name of the animal.
 * @param gender The gender of the animal.
 * @param location The location (address) of the animal.
 * @param createDateTimeDifference The time difference indicating how long ago the animal was created.
 */
@Composable
fun AnimalInfoCard(
    name: String,
    gender: Boolean,
    location: Address?,
    createDateTimeDifference: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = name,
                modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                color = colorResource(id = R.color.text),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.Bottom) {

                val locationIcon: Painter = painterResource(id = R.drawable.location)

                Icon(
                    painter = locationIcon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp, 16.dp),
                    tint = Color.Red
                )

                if (location != null) {
                    Text(
                        text = location.city,
                        modifier = Modifier.padding(8.dp, 12.dp, 12.dp, 0.dp),
                        color = colorResource(id = R.color.text),
                        style = MaterialTheme.typography.caption
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "vor $createDateTimeDifference",
                modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                color = colorResource(id = R.color.text),
                style = MaterialTheme.typography.overline
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            GenderTag(gender)
        }
    }
}