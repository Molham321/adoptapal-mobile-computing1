package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.ui.screens.sharedComponents.composeCall
import de.fhe.adoptapal.ui.screens.sharedComponents.composeEmail
import de.fhe.adoptapal.ui.theme.BackgroundGreyOpacity
import de.fhe.adoptapal.ui.theme.LightModeText

/**
 * Composable function to display an owner card containing information about the animal's supplier.
 *
 * @param animal The [Animal] for which to display the supplier information.
 * @param image The resource ID of the image representing the supplier.
 * @param onItemPressed Callback function to be invoked when the owner card is pressed.
 */
@Composable
fun OwnerCard(
    animal: Animal,
    image: Int,
    onItemPressed: (itemId: Long) -> Unit = {}
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onItemPressed(animal.supplier.id) }),
        elevation = 0.dp,
        backgroundColor = BackgroundGreyOpacity
        // backgroundColor = Color(0xFFE0E0E0)
    ) {
        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                // getting the image from the drawable
                val personImage: Painter = painterResource(id = image)

                Image(
                    modifier = Modifier
                        .size(60.dp, 60.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    painter = personImage,
                    alignment = Alignment.CenterStart,
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier) {
                    Text(
                        text = animal.supplier.name,
                        fontWeight = FontWeight.W600,
                        textAlign = TextAlign.Start,
                        color = LightModeText
                    )
                    if (animal.supplier.phoneNumber != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Telefon: ${animal.supplier.phoneNumber}",
                            color = colorResource(id = R.color.text),
                            style = MaterialTheme.typography.caption
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Email: ${animal.supplier.email}",
                        color = colorResource(id = R.color.text),
                        style = MaterialTheme.typography.caption
                    )
                }
            }

            // create intent row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {


                if (animal.supplier.phoneNumber != null) {
                    Button(
                        onClick = {
                            animal.supplier.phoneNumber?.let { composeCall(context, it) }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = "Anrufen",
                            fontSize = 16.sp,
                        )
                    }
                }
                Button(
                    onClick = {
                        composeEmail(
                            context,
                            animal.supplier.email,
                            "Adoption von ${animal.name} ",
                            "Guten Tag, \nich möchte ${animal.name} ein neues Zuhause bei mir bieten.\nViele Grüße\n"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = stringResource(id = R.string.email),
                        fontSize = 16.sp,
                    )
                }
            }

        }
    }
}
