package de.fhe.adoptapal.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            text = "Profile",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ProfileItem(label = "Name", value = user.name)
        ProfileItem(label = "Email", value = user.email)
        user.phoneNumber?.let { ProfileItem(label = "Phone Number", value = it) }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Address",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        if (user.address == null) {
            Text(
                text = "No address data available",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        } else {
            user.address?.let { ProfileItem(label = "Street", value = it.street) }
            user.address?.let { ProfileItem(label = "House Number", value = it.houseNumber) }
            user.address?.let { ProfileItem(label = "City", value = it.city) }
            user.address?.let { ProfileItem(label = "ZIP", value = it.zipCode) }
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
