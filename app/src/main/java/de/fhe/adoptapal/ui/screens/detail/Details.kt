package de.fhe.adoptapal.ui.screens.detail

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R


// -----------------------------------------------------
// Details
// -----------------------------------------------------
@Composable
fun Details() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.black))
    ) {
        item {
            val dogImage: Painter = painterResource(R.drawable.hund)
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

            InfoCard()
        }

        item {

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "ABOUT NAME \n" +
                        "Beschreibung Beschreibung \n" +
                        "Beschreibung Beschreibung \n" +
                        "Beschreibung Beschreibung \n" +
                        "Beschreibung Beschreibung \n" +
                        "Beschreibung Beschreibung \n",

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Start
            )
        }

        item {

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Rasse: RASSE",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Start
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                GenderTag("GENDER", Color.Red)
                GenderTag("Alter", Color.Blue)
                GenderTag("Gewicht", Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Alter: ALTER",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Geschlecht: GESCHLECHT",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Start
            )
        }

        // Owner info
        item {

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Owner info", color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))

            OwnerCard()
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