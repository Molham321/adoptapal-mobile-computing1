package de.fhe.adoptapal.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class User(
    var name: String,
    var email: String,
    var phoneNumber: String,
    var address: Address
)

data class Address(
    var street: String,
    var houseNumber: String,
    var city: String,
    var zip: String
)

@Composable
fun Settings(user: User, onSave: (User) -> Unit) {
    var name by remember { mutableStateOf(user.name) }
    var email by remember { mutableStateOf(user.email) }
    var phoneNumber by remember { mutableStateOf(user.phoneNumber) }
    var street by remember { mutableStateOf(user.address.street) }
    var houseNumber by remember { mutableStateOf(user.address.houseNumber) }
    var city by remember { mutableStateOf(user.address.city) }
    var zip by remember { mutableStateOf(user.address.zip) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Address",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextField(
            value = street,
            onValueChange = { street = it },
            label = { Text("Street") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = houseNumber,
            onValueChange = { houseNumber = it },
            label = { Text("House Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("City") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = zip,
            onValueChange = { zip = it },
            label = { Text("ZIP") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val updatedUser = User(name, email, phoneNumber, Address(street, houseNumber, city, zip))
                onSave(updatedUser)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }
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
    Settings(user, onSave = {})
}