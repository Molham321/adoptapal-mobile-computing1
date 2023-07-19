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
import androidx.compose.material.MaterialTheme
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
import de.fhe.adoptapal.domain.Animal
import de.fhe.adoptapal.ui.screens.sharedComponents.GenderTag
import de.fhe.adoptapal.ui.theme.LightModeSecondary
import de.fhe.adoptapal.ui.theme.LightModeSecondaryOpacity
import de.fhe.adoptapal.ui.theme.LightModeText
import org.koin.androidx.compose.koinViewModel

//----------------------------------------------
// ItemAnimalCard Component for HomeScreen
//----------------------------------------------
@Composable
fun AnimalCard(
    animal: Animal,
    modifier: Modifier = Modifier,
    onItemPressed: (itemId: Long) -> Unit = {}
) {
    val vm: HomeScreenViewModel = koinViewModel()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onItemPressed(animal.id) }),
        elevation = 0.dp,
        backgroundColor = LightModeSecondaryOpacity,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            val image: Painter = painterResource(id = R.drawable.hund /*animal.image*/)
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
                    color = LightModeText,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = modifier.height(8.dp))

                Text(
                    text = buildString {
                        append(vm.getAge(animal.birthday))
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
                        tint = LightModeText
                    )

                    animal.supplier.address?.city?.let {
                        Text(
                            text = it,
                            modifier = modifier.padding(8.dp, 12.dp, 12.dp, 0.dp),
                            color = Color.Gray,
                        )
                    }
                }
            }
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                GenderTag(animal.isMale)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "vor 12 Minuten",
                    modifier = modifier.padding(8.dp, 0.dp, 12.dp, 12.dp),
                    color = Color.Gray,
                    style = MaterialTheme.typography.overline
                )
            }
        }
    }
}