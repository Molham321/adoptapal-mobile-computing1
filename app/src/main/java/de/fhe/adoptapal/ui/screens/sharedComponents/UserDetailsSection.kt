package de.fhe.adoptapal.ui.screens.sharedComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.home.AnimalCard
import de.fhe.adoptapal.ui.screens.util.FullscreenPlaceholderView

@Composable
fun UserDetailsAndAnimalCards(
    user: User?,
    animalList: List<Animal>,
    modifier: Modifier = Modifier,
    onAnimalClick: (Long) -> Unit
) {
    Column {
        UserDetailsSection(user, modifier)

        if (animalList.isNotEmpty()) {
            Text(
                text = "Hochgeladene Tiere",
                modifier = Modifier.padding(16.dp, 0.dp, 12.dp, 0.dp),
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5,
                fontSize = 20.sp
            )

            animalList.forEach { animal ->
                AnimalCard(
                    animal,
                    modifier = modifier,
                    user?.address
                ) {
                    onAnimalClick(it)
                }
            }
        } else {
            FullscreenPlaceholderView("No Animals", Icons.Filled.Info)
        }
    }
}

@Composable
fun UserDetailsSection(user: User?, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        user?.let {
            Text(
                text = "${stringResource(id = R.string.email)}: ${it.email}",
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
                text = "Telefon: ${it.phoneNumber ?: "N/A"}",
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