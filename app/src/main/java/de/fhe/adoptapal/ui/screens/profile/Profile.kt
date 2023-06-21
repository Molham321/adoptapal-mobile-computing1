package de.fhe.adoptapal.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

data class User(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val address: Address
)

data class Address(
    val street: String,
    val houseNumber: String,
    val city: String,
    val zip: String
)

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
        ProfileItem(label = "Phone Number", value = user.phoneNumber)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Address",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        ProfileItem(label = "Street", value = user.address.street)
        ProfileItem(label = "House Number", value = user.address.houseNumber)
        ProfileItem(label = "City", value = user.address.city)
        ProfileItem(label = "ZIP", value = user.address.zip)
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

@Preview
@Composable
fun ProfilePreview() {
    val user = User(
        name = "John Doe",
        email = "johndoe@example.com",
        phoneNumber = "1234567890",
        address = Address("123 Main St", "Apt 4B", "Cityville", "12345")
    )
    Profile(user)
}
