package de.fhe.adoptapal.ui.screens.userDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R

@Preview
@Composable
fun UserInfo(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Column(modifier = modifier) {
                Text(
                    text = "Email:",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.overline,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 10.sp
                )
                Text(
                    text = "mail@email.com",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Start,
                    fontSize = 10.sp
                )
                Spacer(modifier = modifier.height(24.dp))
                Text(
                    text = "Telefon:",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.overline,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 10.sp
                )
                Text(
                    text = "0361/123456789",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = colorResource(id = R.color.white),
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Start,
                    fontSize = 10.sp
                )
            }
        }
    }
}