package de.fhe.adoptapal.ui.screens.settings

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.theme.LightModeSecondary
import org.koin.androidx.compose.koinViewModel

@Composable
fun Settings(
    user: User,
    updateUser: (user: User) -> Unit = {}
) {
    val vm: SettingsScreenViewModel = koinViewModel()
    var name by remember { mutableStateOf(user.name) }
    var email by remember { mutableStateOf(user.email) }
    var phoneNumber by remember { mutableStateOf(user.phoneNumber ?: "") }


    var street by remember { mutableStateOf(user.address?.street ?: "") }
    var houseNumber by remember { mutableStateOf(user.address?.houseNumber ?: "") }
    var city by remember { mutableStateOf(user.address?.city ?: "") }
    var zip by remember { mutableStateOf(user.address?.zipCode ?: "") }


    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var phoneNumberError by remember { mutableStateOf("") }

    var streetError by remember { mutableStateOf("") }
    var houseNumberError by remember { mutableStateOf("") }
    var cityError by remember { mutableStateOf("") }
    var zipError by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Profil",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = name,
            onValueChange = {
                name = it
                nameError = ""
            },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        if (nameError.isNotBlank()) {
            Text(
                text = nameError,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = email,
            onValueChange = {
                email = it
                emailError = ""
            },
            label = { Text("E-Mail-Adresse") },
            modifier = Modifier.fillMaxWidth()
        )

        if (emailError.isNotBlank()) {
            Text(
                text = emailError,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        phoneNumber.let {
            TextField(
                value = it,
                onValueChange = {
                    phoneNumber = it
                    phoneNumberError = ""
                },
                label = { Text("Telefonnummer") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (phoneNumberError.isNotBlank()) {
            Text(
                text = phoneNumberError,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Adresse",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TextField(
            value = street,
            onValueChange = {
                street = it
                streetError = ""
            },
            label = { Text("Straße") },
            modifier = Modifier.fillMaxWidth()
        )

        if (streetError.isNotBlank()) {
            Text(
                text = streetError,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = houseNumber,
            onValueChange = {
                houseNumber = it
                houseNumberError = ""
            },
            label = { Text("Hausnummer") },
            modifier = Modifier.fillMaxWidth()
        )

        if (houseNumberError.isNotBlank()) {
            Text(
                text = houseNumberError,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = city,
            onValueChange = {
                city = it
                cityError = ""
            },
            label = { Text("Stadt") },
            modifier = Modifier.fillMaxWidth()
        )

        if (cityError.isNotBlank()) {
            Text(
                text = cityError,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = zip,
            onValueChange = {
                zip = it
                zipError = ""
            },
            label = { Text("ZIP") },
            modifier = Modifier.fillMaxWidth()
        )

        if (zipError.isNotBlank()) {
            Text(
                text = zipError,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                val isNameValid = vm.validateName(name)
                val isEmailValid = vm.validateEmail(email)
                val isPhoneNumberValid = vm.validatePhoneNumber(phoneNumber)

                val isStreetValid = vm.validateStreet(street)
                val isCityValid = vm.validateCity(city)
                val isHouseNumberValid = vm.validateHouseNumber(houseNumber)
                val isZipValid = vm.validateZip(zip)

                if (isNameValid &&
                    isEmailValid &&
                    isPhoneNumberValid
                ) {
                    user.name = name
                    user.email = email
                    user.phoneNumber = phoneNumber

                    // create or update address if all address fields are set
                    if (street != "" || houseNumber != "" || city != "" || zip != "") {
                        if (isStreetValid && isCityValid && isHouseNumberValid && isZipValid) {
                            user.address = Address(houseNumber, street, city, zip)
                            updateUser(user)
                        } else {
                            if (!isStreetValid) {
                                streetError = "Invalid street"
                            }
                            if (!isCityValid) {
                                cityError = "Invalid city"
                            }
                            if (!isHouseNumberValid) {
                                houseNumberError = "Invalid HouseNumber"
                            }
                            if (!isZipValid) {
                                zipError = "Invalid ZIP"
                            }
                        }
                    } else {
                        updateUser(user)
                    }

                } else {
                    if (!isNameValid) {
                        nameError = "Invalid Name"
                    }
                    if (!isEmailValid) {
                        emailError = "Invalid email address"
                    }
                    if (!isPhoneNumberValid) {
                        phoneNumberError = "Invalid Phone Number"
                    }
                }
            },
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(backgroundColor = LightModeSecondary)
        ) {
            Text(text = "Speichern")
        }
    }
}