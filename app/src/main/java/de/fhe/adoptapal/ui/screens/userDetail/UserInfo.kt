package de.fhe.adoptapal.ui.screens.userDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R

@Preview
@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    user: String = "Anbieter",
    email: String = "mail@email.com",
    telephone: String = "0361/123456789"
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
                        text = user.uppercase(),
                        modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h1,
                        fontSize = 30.sp
                    )
                    Spacer(Modifier.weight(1f))
                    Row(modifier = Modifier, horizontalArrangement = Arrangement.End) {
                        Icon(modifier = Modifier.size(32.dp, 32.dp), imageVector = Icons.Outlined.LocationOn, contentDescription = null, tint = Color.Black)
                    }

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
                    text = "Über " + user + ":",
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
                    text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
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
                    text = email,
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
                    text = "Telefon:",
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
                    text = telephone,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = colorResource(id = R.color.black),
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp
                )
            }
        }
    }
}