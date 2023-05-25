package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.data.FakeDatabase


// -----------------------------------------------------
// Details
// -----------------------------------------------------
@Composable
fun Details(animalId: Int, modifier: Modifier = Modifier ) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
    ) {

        val animal = FakeDatabase.AnimalList[animalId]

        item {
            val dogImage: Painter = painterResource(animal.image)
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(346.dp),
                painter = dogImage,
                alignment = Alignment.CenterStart,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

            AnimalInfoCard(animal.name, animal.gender, animal.location)
        }

        // My story details
        item {

            Spacer(modifier = Modifier.height(24.dp))
            Title(title = "My Story")
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "ABOUT ${animal.name} \n" +
                        "${animal.about}",

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
                text = "Rasse: ${animal.breed}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                color = colorResource(id = R.color.text),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Start
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                GenderTag(animal.gender, Color.Red)
                GenderTag(animal.age.toString(), Color.Blue)
                GenderTag(animal.weight.toString(), Color.DarkGray)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Alter: ${animal.age}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                color = colorResource(id = R.color.text),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Geschlecht: ${animal.gender}",
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
            Title(title = "Animal info")
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoCard(title = "Age", value = animal.age.toString().plus(" yrs"))
                InfoCard(title = "Color", value = animal.color)
                InfoCard(title = "Weight", value = animal.weight.toString().plus("Kg"))
            }
        }

        // Owner info
        item {

            Spacer(modifier = Modifier.height(24.dp))
            Title(title = "Owner info")
            Spacer(modifier = Modifier.height(16.dp))

            OwnerCard(animal.owner.name, animal.owner.bio, animal.owner.image)
        }

        // CTA - Adopt me button
        item {
            Spacer(modifier = Modifier.height(36.dp))
            Button(
                onClick = { /* Do something! */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text("Adopt me")
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun Title(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 0.dp, 0.dp),
        color = colorResource(id = R.color.text),
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.W600,
        textAlign = TextAlign.Start
    )
}