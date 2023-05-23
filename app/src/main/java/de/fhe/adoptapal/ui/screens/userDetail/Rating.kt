package de.fhe.adoptapal.ui.screens.userDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    filledStarsColor: Color = Color.Yellow,
    unfilledStarsColor: Color = Color.LightGray
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))
    Column(modifier = modifier) {
        Row(modifier = modifier) {
            repeat(filledStars) {
                Icon(imageVector = Icons.Outlined.Star, contentDescription = null, modifier = Modifier.size(32.dp, 32.dp), tint = filledStarsColor)
            }

            repeat(unfilledStars) {
                Icon(imageVector = Icons.Outlined.Star, contentDescription = null, modifier = Modifier.size(32.dp, 32.dp), tint = unfilledStarsColor)
            }
            /*        if (halfStar) {
                        Icon(
                            imageVector = Icons.Outlined.StarHalf,
                            contentDescription = null,
                            tint = starsColor
                        )
                    }
                    repeat(unfilledStars) {
                        Icon(
                            imageVector = Icons.Outlined.StarOutline,
                            contentDescription = null,
                            tint = starsColor
                        )
                    }*/
        }
        Row(modifier = modifier) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$rating Sterne (200 Bewertungen)",
                modifier = Modifier.padding(4.dp, 2.dp, 12.dp, 0.dp),
                color = MaterialTheme.colors.surface,
                style = MaterialTheme.typography.overline,
                fontSize = 10.sp
            )
        }
    }

}
@Preview
@Composable
fun RatingPreview() {
    RatingBar(rating = 4.0)
}