package de.fhe.adoptapal.ui.screens.addAnimal

import android.widget.Toast
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.ui.screens.core.LocalScaffoldState

@Composable
fun AddAnimalScreen(vm: AddAnimalScreenViewModel, modifier: Modifier = Modifier) {

    val animalCategoryList = remember { vm.animalCategoryList }
    val animalColorList = remember { vm.animalColorList }


    var animalNameTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var animalNameEditingState by remember { mutableStateOf(false) }
    var animalDescriptionTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var animalDescriptionEditingState by remember { mutableStateOf(false) }
    var animalCategoryDropdownValue by remember { mutableStateOf<Long>(0) }
    var animalCategoryEditingState by remember { mutableStateOf(false) }
    var animalColorDropdownValue by remember { mutableStateOf<Long>(0) }
    var animalColorEditingState by remember { mutableStateOf(false) }
    var animalBirthdateValue by remember { mutableStateOf("") }
    var animalBirthdateEditingState by remember { mutableStateOf(false) }
    var animalWeightTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var animalWeightEditingState by remember { mutableStateOf(false) }
    var animalGenderValue by remember { mutableStateOf<Boolean>(false) }
    var animalGenderEditingState by remember { mutableStateOf(false) }

    val scaffoldState = LocalScaffoldState.current
    val contextForToast = LocalContext.current.applicationContext

    var birthdateError by remember { mutableStateOf("") }
    var weightError by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        Spacer(Modifier.height(20.dp))

        Text(
            text = "Ein neues Tier hochladen",

            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 0.dp),
            fontSize = 25.sp,
            color = colorResource(id = R.color.black),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(20.dp))

        InputField(animalNameTextFieldValue, animalNameEditingState, "Name des Tieres") {
            animalNameTextFieldValue = it
            animalNameEditingState = true
        }

        Spacer(Modifier.height(15.dp))

        InputField(
            animalDescriptionTextFieldValue,
            animalDescriptionEditingState,
            "Beschreibung des Tieres"
        ) {
            animalDescriptionTextFieldValue = it
            animalDescriptionEditingState = true
        }

        Spacer(Modifier.height(15.dp))

        // DropdownSelect("Tierrasse", animalCategoryDropdownValue, arrayOf("Hund", "Katze", "Nagetier", "Reptil", "Vogel", "Fisch"))
        DropdownSelect(
            "Tierrasse",
            animalCategoryDropdownValue,
            animalCategoryEditingState,
            vm.getCategoryArray(animalCategoryList.value),
            vm.getCategoryMap(animalCategoryList.value)
        ) {
            animalCategoryDropdownValue = it
            animalCategoryEditingState = true
        }

        Spacer(Modifier.height(15.dp))

        // DropdownSelect("Fellfarbe", animalColorDropdownValue, arrayOf("schwarz", "weiß", "blond", "orange", "braun", "gemustert", "kein Fell"))
        DropdownSelect(
            "Farbe des Tieres",
            animalColorDropdownValue,
            animalColorEditingState,
            vm.getColorArray(animalColorList.value),
            vm.getColorMap(animalColorList.value)
        ) {
            animalColorDropdownValue = it
            animalColorEditingState = true
        }

        Spacer(Modifier.height(15.dp))

        DatePicker(animalBirthdateValue, animalBirthdateEditingState) {
            animalBirthdateValue = it
            birthdateError = ""
            animalBirthdateEditingState = true
        }

        if (birthdateError.isNotBlank()) {
            Text(
                text = birthdateError,
                color = Color.Red,
                style = MaterialTheme.typography.caption,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
            )
        }

        Spacer(Modifier.height(15.dp))

        InputField(animalWeightTextFieldValue, animalWeightEditingState, "Gewicht") {
            animalWeightTextFieldValue = it
            weightError = ""
            animalWeightEditingState = true
        }

        if (weightError.isNotBlank()) {
            Text(
                text = weightError,
                color = Color.Red,
                style = MaterialTheme.typography.caption,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
            )
        }

        Spacer(Modifier.height(15.dp))

        Switch(animalGenderValue, animalGenderEditingState) {
            animalGenderValue = it
            animalGenderEditingState = true
        }

        Spacer(Modifier.height(15.dp))

        if (
            animalNameTextFieldValue.text == "" ||
            animalDescriptionTextFieldValue.text == "" ||
            animalCategoryDropdownValue <= 0 ||
            animalColorDropdownValue <= 0 ||
            animalBirthdateValue == "" ||
            animalWeightTextFieldValue.text == ""
        ) {
            Button(
                enabled = false,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClick = {

                }
            ) {
                Text(text = "Tier speichern")
            }
        } else {
            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClick = {
                    // println(message = "$animalNameTextFieldValue")
//                Toast.makeText(contextForToast, "${animalBirthdateValue} ${animalDescriptionTextFieldValue.text}", Toast.LENGTH_SHORT)
//                    .show()

                    val isBirthdateValid = vm.validateBirthdate(animalBirthdateValue)
                    val isWeightValid = vm.validateWeight(animalWeightTextFieldValue.text)

                    if(isBirthdateValid && isWeightValid ) {
                        vm.addAnimal(
                            animalNameTextFieldValue.text,
                            animalDescriptionTextFieldValue.text,
                            animalCategoryDropdownValue,
                            animalColorDropdownValue,
                            animalBirthdateValue,
                            animalWeightTextFieldValue.text.toFloat(),
                            animalGenderValue
                        )

                        Toast.makeText(
                            contextForToast,
                            "Tier ${animalNameTextFieldValue.text} wurde gespeichert",
                            Toast.LENGTH_LONG
                        )
                            .show()

                        // clear form
                        animalNameTextFieldValue = TextFieldValue("")
                        animalDescriptionTextFieldValue = TextFieldValue("")
                        animalCategoryDropdownValue = 0
                        animalColorDropdownValue = 0
                        animalBirthdateValue = ""
                        animalWeightTextFieldValue = TextFieldValue("")
                        animalGenderValue = false

                        animalNameEditingState = false // To hide keyboard
                        animalDescriptionEditingState = false
                        animalCategoryEditingState = false
                        animalColorEditingState = false
                        animalBirthdateEditingState = false
                        animalWeightEditingState = false
                        animalGenderEditingState = false
                    } else {
                        if (!isBirthdateValid) {
                            birthdateError = "ungültige Geburtsdatum"
                        }
                        if (!isWeightValid) {
                            weightError = "ungültige eingabe"
                        }
                    }
                }
            ) {
                Text(text = "Tier speichern")
            }
        }



        Button(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
            onClick = {
                vm.navigateToUserList()
            }
        ) {
            Text(text = "zurück")
        }
    }
}
