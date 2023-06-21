package de.fhe.adoptapal.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun SettingsScreen() {
    Column() {
        val user = User(
            name = "John Doe",
            email = "johndoe@example.com",
            phoneNumber = "1234567890",
            address = Address("123 Main St", "Apt 4B", "Cityville", "12345")
        )
        Settings(user, onSave = {})
    }
}