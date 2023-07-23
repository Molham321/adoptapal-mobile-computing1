package de.fhe.adoptapal.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.userDetail.RatingBar

@Composable
fun ProfileInfo(
    user: User,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Column(modifier = modifier) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = user.name.uppercase(),
                        modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h1,
                        fontSize = 30.sp
                    )
                    Spacer(Modifier.weight(1f))

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    RatingBar(rating = 4.0)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "about ${user.name}: Bio ist in DB nicht vorhanden",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = colorResource(id = R.color.black),
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp
                )
                Spacer(modifier = modifier.height(24.dp))
                Text(
                    text = "Email:",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = colorResource(id = R.color.black),
                    style = MaterialTheme.typography.overline,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp
                )
                Text(
                    text = user.email,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = colorResource(id = R.color.black),
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp
                )
                Spacer(modifier = modifier.height(24.dp))
                Text(
                    text = "Telefon: ${user.phoneNumber}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = colorResource(id = R.color.black),
                    style = MaterialTheme.typography.overline,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp
                )
            }
        }
    }
}