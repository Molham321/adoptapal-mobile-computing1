package de.fhe.adoptapal.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import de.fhe.adoptapal.ui.screens.login.LoginScreenViewModel

@Composable
fun ProfileScreen() {
    Column() {
        val user = User(
            name = "John Doe",
            email = "johndoe@example.com",
            phoneNumber = "1234567890",
            address = Address("123 Main St", "Apt 4B", "Cityville", "12345")
        )
        println(LoginScreenViewModel.loggedIn)
        Profile(user)
    }
}