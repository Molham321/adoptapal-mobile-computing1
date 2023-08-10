package de.fhe.adoptapal.ui.screens.settings

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.Address
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.sharedComponents.InputField
import de.fhe.adoptapal.ui.theme.LightModeSecondary
import org.koin.androidx.compose.koinViewModel

/**
 * A Composable function that displays user profile and address settings. Users can edit their profile information
 * including name, email, phone number, and address details.
 *
 * @param user The [User] object representing the user's information.
 * @param updateUser A callback function to update the user's information.
 */
@Composable
fun Settings(
    user: User,
    updateUser: (user: User) -> Unit = {}
) {
    val vm: SettingsScreenViewModel = koinViewModel()

    // Mutable state variables to hold the user input values and potential errors
    var name by remember { mutableStateOf(TextFieldValue(user.name)) }
    var email by remember { mutableStateOf(TextFieldValue(user.email)) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue(user.phoneNumber ?: "")) }

    var street by remember { mutableStateOf(TextFieldValue(user.address?.street ?: "")) }
    var houseNumber by remember { mutableStateOf(TextFieldValue(user.address?.houseNumber ?: "")) }
    var city by remember { mutableStateOf(TextFieldValue(user.address?.city ?: "")) }
    var zip by remember { mutableStateOf(TextFieldValue(user.address?.zipCode ?: "")) }

    // Mutable state variables to hold validation error messages
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
        // Display profile section
        Text(
            text = stringResource(R.string.profile),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        InputField(
            text = name,
            onTextChange = {
                name = it
                nameError = ""
            },
            inputPlaceholder = stringResource(R.string.name),
            editing = true,
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

        InputField(
            text = email,
            onTextChange = {
                email = it
                emailError = ""
            },
            inputPlaceholder = stringResource(R.string.e_mail_adresse),
            editing = true,
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
        InputField(
            text = phoneNumber,
            onTextChange = {
                phoneNumber = it
                phoneNumberError = ""
            },
            inputPlaceholder = stringResource(R.string.phone_number),
            editing = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (phoneNumberError.isNotBlank()) {
            Text(
                text = phoneNumberError,
                color = Color.Red,
                style = MaterialTheme.typography.caption
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.address),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        InputField(
            text = street,
            onTextChange = {
                street = it
                streetError = ""
            },
            inputPlaceholder = stringResource(R.string.street),
            editing = true,
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
        InputField(
            text = houseNumber,
            onTextChange = {
                houseNumber = it
                houseNumberError = ""
            },
            inputPlaceholder = stringResource(R.string.house_number),
            editing = true,
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
        InputField(
            text = city,
            onTextChange = {
                city = it
                cityError = ""
            },
            inputPlaceholder = stringResource(id = R.string.city),
            editing = true,
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
        InputField(
            text = zip,
            onTextChange = {
                zip = it
                zipError = ""
            },
            inputPlaceholder = stringResource(R.string.plz),
            editing = true,
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

                val isNameValid = vm.validateName(name.text)
                val isEmailValid = vm.validateEmail(email.text)
                val isPhoneNumberValid = vm.validatePhoneNumber(phoneNumber.text)

                val isStreetValid = vm.validateStreet(street.text)
                val isCityValid = vm.validateCity(city.text)
                val isHouseNumberValid = vm.validateHouseNumber(houseNumber.text)
                val isZipValid = vm.validateZip(zip.text)

                if (isNameValid &&
                    isEmailValid &&
                    isPhoneNumberValid
                ) {
                    user.name = name.text
                    user.email = email.text
                    user.phoneNumber = phoneNumber.text

                    // create or update address if all address fields are set
                    if (street.text != "" || houseNumber.text != "" || city.text != "" || zip.text != "") {
                        if (isStreetValid && isCityValid && isHouseNumberValid && isZipValid) {
                            user.address = Address(houseNumber.text, street.text, city.text, zip.text)
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
            Text(text = stringResource(R.string.save))
        }
    }
}