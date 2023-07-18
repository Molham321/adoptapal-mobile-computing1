package de.fhe.adoptapal.ui.screens.userDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.sharedComponents.composeCall
import de.fhe.adoptapal.ui.screens.sharedComponents.composeEmail

@Composable
fun UserInfo(
    user: User,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
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
                    Row(modifier = Modifier, horizontalArrangement = Arrangement.End) {
                        Icon(
                            modifier = Modifier.size(32.dp, 32.dp),
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = Color.Black
                        )
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

                // create intent row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {


                    if (user.phoneNumber != null) {
                        Button(
                            onClick = {
                                user.phoneNumber?.let { composeCall(context, it) }
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
                                user.email,
                                "Adoption von...",
                                "Guten Tag, \nich möchte einem Ihrer Tiere ein neues Zuhause bei mir bieten.\nViele Grüße\n"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = "Email",
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
    }
}