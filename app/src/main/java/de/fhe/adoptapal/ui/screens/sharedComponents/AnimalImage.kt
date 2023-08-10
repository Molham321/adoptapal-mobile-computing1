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

@Composable
fun AnimalImage(
    modifier: Modifier,
    category: String,
    imageFilePath: String?
) {
    val defaultImage = when (category) {
        "Katze" -> painterResource(id = R.drawable.andresllanezas_katze)
        "Hund" -> painterResource(id = R.drawable.andresllanezas_hund)
        "Fisch" -> painterResource(id = R.drawable.andresllanezas_fisch)
        "Reptil" -> painterResource(id = R.drawable.andresllanezas_reptil)
        "Nagetier" -> painterResource(id = R.drawable.andresllanezas_nagetier)
        "Vogel" -> painterResource(id = R.drawable.andresllanezas_vogel)
        else -> painterResource(id = R.drawable.andresllanezas_andere)
    }

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
    } else {
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