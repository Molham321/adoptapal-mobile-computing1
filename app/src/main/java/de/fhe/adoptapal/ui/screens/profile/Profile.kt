package de.fhe.adoptapal.ui.screens.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.domain.User

@Composable
fun Profile(user: User) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Profil",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ProfileItem(label = "Name", value = user.name)
        ProfileItem(label = "Email", value = user.email)

        val phoneNumber = user.phoneNumber ?: "N/A"
        ProfileItem(label = "Telefon", value = phoneNumber)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Adresse",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val street = user.address?.street ?: "N/A"
        ProfileItem(label = "Straße", value = street)

        val houseNumber = user.address?.houseNumber ?: "N/A"
        ProfileItem(label = "Hausnummer", value = houseNumber)

        val city = user.address?.city ?: "N/A"
        ProfileItem(label = "Stadt", value = city)

        val zipCode = user.address?.zipCode ?: "N/A"
        ProfileItem(label = "PLZ", value = zipCode)
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.body1,
            color = Color.Black,
            modifier = Modifier
                .width(120.dp)
                .padding(end = 8.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body2,
            color = Color.Gray,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
