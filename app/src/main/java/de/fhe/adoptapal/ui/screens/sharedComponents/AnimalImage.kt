package de.fhe.adoptapal.ui.screens.sharedComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.fhe.adoptapal.R

/**
 * A Composable function that displays an animal image with the option to use a default image based on the category.
 *
 * @param modifier The [Modifier] to be applied to the image.
 * @param category The category of the animal (e.g., "Katze", "Hund", "Fisch").
 * @param imageFilePath The file path of the animal image, or null if not available.
 */
@Composable
fun AnimalImage(
    modifier: Modifier,
    category: String,
    imageFilePath: String?
) {
    // Determine the default image based on the category
    val defaultImage = when (category) {
        "Katze" -> painterResource(id = R.drawable.andresllanezas_katze)
        "Hund" -> painterResource(id = R.drawable.andresllanezas_hund)
        "Fisch" -> painterResource(id = R.drawable.andresllanezas_fisch)
        "Reptil" -> painterResource(id = R.drawable.andresllanezas_reptil)
        "Nagetier" -> painterResource(id = R.drawable.andresllanezas_nagetier)
        "Vogel" -> painterResource(id = R.drawable.andresllanezas_vogel)
        else -> painterResource(id = R.drawable.andresllanezas_andere)
    }

    // Display the default image if imageFilePath is null
    if (imageFilePath == null) {
        Image(
            modifier = modifier
                .size(80.dp, 80.dp)
                .clip(RoundedCornerShape(16.dp)),
            painter = defaultImage,
            alignment = Alignment.CenterStart,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    } else {  // Display the image from the provided file path
        AsyncImage(
            model = imageFilePath,
            contentDescription = null,
            modifier = modifier
                .size(80.dp, 80.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
    }
}