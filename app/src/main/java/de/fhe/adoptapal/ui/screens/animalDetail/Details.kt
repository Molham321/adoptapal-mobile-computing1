package de.fhe.adoptapal.ui.screens.animalDetail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.ui.screens.sharedComponents.Title
import de.fhe.adoptapal.ui.theme.BackgroundGreyOpacity
import de.fhe.adoptapal.ui.theme.LightModeBackground
import org.koin.androidx.compose.koinViewModel


// -----------------------------------------------------
// Details
// -----------------------------------------------------
@Composable
fun Details(
    animal: Animal,
    modifier: Modifier = Modifier,
    onItemPressed: (itemId: Long) -> Unit = {}
) {
    var animalIsFavorite = remember { mutableStateOf(false) }

    val vm: DetailScreenViewModel = koinViewModel()

    val contextForToast = LocalContext.current.applicationContext

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            // .background(color = colorResource(id = R.color.background))
    ) {

        item {
            var image: Painter
            when(animal.animalCategory.name) {
                "Katze" -> {image = painterResource(id = R.drawable.andresllanezas_katze)}
                "Hund" -> {image = painterResource(id = R.drawable.andresllanezas_hund)}
                "Fisch" -> {image = painterResource(id = R.drawable.andresllanezas_fisch)}
                "Reptil" -> {image = painterResource(id = R.drawable.andresllanezas_reptil)}
                "Nagetier" -> {image = painterResource(id = R.drawable.andresllanezas_nagetier)}
                "Vogel" -> {image = painterResource(id = R.drawable.andresllanezas_vogel)}
                else -> {image = painterResource(id = R.drawable.andresllanezas_andere)}
            }
            // val image: Painter = painterResource(R.drawable.hund /*animal.imageFilePath*/)
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(346.dp),
                painter = image,
                alignment = Alignment.CenterStart,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Surface(
                shape = CircleShape,
                modifier = Modifier
                    .padding(6.dp)
                    .size(52.dp),
                color = BackgroundGreyOpacity
            ) {
                IconToggleButton(
                    checked = animal.isFavorite,
                    onCheckedChange = {
                        animalIsFavorite.value = it
                        vm.saveAnimalAsFavorite(animal)
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp, 36.dp),
                        painter = if (animal.isFavorite) {
                            painterResource(id = R.drawable.baseline_bookmark_24)
                        } else {
                            painterResource(id = R.drawable.baseline_bookmark_border_24)
                        },
                        contentDescription = null
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            animal.supplier.address?.city?.toString()?.let {
                AnimalInfoCard(
                    animal.name,
                    animal.isMale,
                    it
                )
            }
        }

        // My story details
        item {

            Spacer(modifier = Modifier.height(24.dp))
            Title(title = "Über mich...")
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                // text = "ABOUT ${animal.name} \n" +
                //         "${animal.description}",
                text = "${animal.description}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                color = colorResource(id = R.color.text),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Start
            )
        }

        item {

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                // text = "Rasse: ${"Hunde Breed"/*animal.breed*/}",
                text = "Tierart: ${animal.animalCategory.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                color = colorResource(id = R.color.text),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Geburtstag: ${animal.birthday}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                color = colorResource(id = R.color.text),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Start
            )
        }

        // Quick info
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Title(title = "Metriken")
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoCard(title = "Alter", value = vm.getAge(animal.birthday))
                InfoCard(title = "Farbe", value = animal.color.name)
                InfoCard(title = "Gewicht", value = animal.weight.toString().plus("Kg"))
            }
        }

        // Owner info
        item {

            Spacer(modifier = Modifier.height(24.dp))
            Title(title = "Anbieter")

            OwnerCard(
                animal,
                R.drawable.user,
                onItemPressed
            )
        }

//        // CTA - Adopt me button
//        item {
//            Spacer(modifier = Modifier.height(36.dp))
//            Button(
//                onClick = { /* Do something! */ },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(52.dp)
//                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
//                colors = ButtonDefaults.textButtonColors(
//                    backgroundColor = Color.LightGray,
//                    contentColor = Color.Black
//                )
//            ) {
//                Text("Adopt me")
//            }
//            Spacer(modifier = Modifier.height(24.dp))
//        }
    }
}