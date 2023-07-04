package de.fhe.adoptapal.ui.screens.animalDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.User


@Composable
fun OwnerCard(
    user: User,
    name: String,
    bio: String,
    image: Int,
    onItemPressed: (itemId: Long) -> Unit = {}
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onItemPressed(user.id) }),
        elevation = 0.dp,
        backgroundColor = Color(0xFFE0E0E0)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            // getting the image from the drawable
            val personImage: Painter = painterResource(id = image)

            Image(
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .clip(RoundedCornerShape(16.dp)),
                painter = personImage,
                alignment = Alignment.CenterStart,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier) {
                Text(
                    text = name,
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "bio: ${bio}",
                    color = colorResource(id = R.color.text),
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}