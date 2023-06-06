package de.fhe.adoptapal.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.model.Animal

//----------------------------------------------
// ItemAnimalCard Component for HomeScreen
//----------------------------------------------
@Composable
fun AnimalCard(
    animal: Animal,
    modifier: Modifier = Modifier,
    onItemPressed: (itemId: Int) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onItemPressed(animal.id) }),
        elevation = 0.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            val image: Painter = painterResource(id = animal.image)
            Image(
                modifier = modifier
                    .size(80.dp, 80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                painter = image,
                alignment = Alignment.CenterStart,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = modifier.width(16.dp))

            Column(modifier = modifier.align(Alignment.CenterVertically)) {
                Text(
                    text = animal.name,
                    modifier = modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = modifier.height(8.dp))

                Text(
                    text = buildString {
                        append(animal.age)
                        append(" | ")
                        append(animal.breed)
                        append(" | ")
                        append(animal.art)
                    },
                    modifier = modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    color = Color.Gray,
                )

                Row(verticalAlignment = Alignment.Bottom) {

                    val location: Painter = painterResource(id = R.drawable.location)

                    Icon(
                        painter = location,
                        contentDescription = null,
                        modifier = modifier.size(16.dp, 16.dp),
                        tint = Color.Black
                    )

                    Text(
                        text = animal.location,
                        modifier = modifier.padding(8.dp, 12.dp, 12.dp, 0.dp),
                        color = Color.Gray,
                    )
                }
            }
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = animal.gender,
                    modifier = modifier.padding(8.dp, 12.dp, 12.dp, 0.dp),
                    color = Color.Black,
                )
            }
        }
    }
}