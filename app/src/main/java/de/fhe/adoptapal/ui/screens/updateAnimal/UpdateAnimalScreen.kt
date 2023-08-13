package de.fhe.adoptapal.ui.screens.updateAnimal

import android.net.Uri
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.AnimalCategory
import de.fhe.adoptapal.ui.screens.addAnimal.DatePicker
import de.fhe.adoptapal.ui.screens.addAnimal.DropdownSelect
import de.fhe.adoptapal.ui.screens.addAnimal.Switch
import de.fhe.adoptapal.ui.screens.sharedComponents.InputField
import de.fhe.adoptapal.ui.theme.LightModeSecondary
import java.time.format.DateTimeFormatter

@Composable
fun UpdateAnimalScreen(vm: UpdateAnimalScreenViewModel, modifier: Modifier = Modifier) {

    val animal = remember { vm.animal }

    val animalCategoryList = remember { vm.animalCategoryList }
    val animalColorList = remember { vm.animalColorList }

    if (animal.value != null) {
        // Initialize the variables with values from the animal object

        var animalNameTextFieldValue by remember { mutableStateOf(TextFieldValue(animal.value?.name ?: "")) }
        var animalDescriptionTextFieldValue by remember { mutableStateOf(TextFieldValue(animal.value?.description ?: "")) }

        var animalCategoryDropdownValue by remember { mutableStateOf(animal.value?.animalCategory?.id ?: 0L) }
        var animalCategoryEditingState by remember { mutableStateOf(false) }

        var animalColorDropdownValue by remember { mutableStateOf(animal.value?.color?.id ?: 0L) }
        var animalColorEditingState by remember { mutableStateOf(false) }

        var animalBirthdateValue by remember { mutableStateOf(animal.value?.birthday?.format(
            DateTimeFormatter.ofPattern("dd.MM.yyyy")) ?: "") }
        var animalBirthdateEditingState by remember { mutableStateOf(false) }

        var animalWeightTextFieldValue by remember { mutableStateOf(TextFieldValue(animal.value?.weight.toString() ?: "")) }

        var animalGenderValue by remember { mutableStateOf(animal.value?.isMale ?: false) }
        var animalGenderEditingState by remember { mutableStateOf(false) }


        var animalNameError by remember { mutableStateOf("") }
        var animalDescriptionError by remember { mutableStateOf("") }
        var animalCategoryError by remember { mutableStateOf("") }
        var animalBirthdateError by remember { mutableStateOf("") }
        var animalWeightError by remember { mutableStateOf("") }


        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            Spacer(Modifier.height(20.dp))

            Text(
                text = "Update dein Tier!",

                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                fontSize = 25.sp,
                color = colorResource(id = R.color.black),
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center
            )


            Spacer(Modifier.height(20.dp))

            // Update Animal Name
            InputField(
                text = animalNameTextFieldValue,
                onTextChange = {
                    animalNameTextFieldValue = it
                    animalNameError = ""
                },
                inputPlaceholder = stringResource(R.string.name),
                editing = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (animalNameError.isNotBlank()) {
                Text(
                    text = animalNameError,
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            //Update Animal Description
            InputField(
                text = animalDescriptionTextFieldValue,
                onTextChange = {
                    animalDescriptionTextFieldValue = it
                    animalDescriptionError = ""
                },
                inputPlaceholder = stringResource(R.string.description),
                editing = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (animalDescriptionError.isNotBlank()) {
                Text(
                    text = animalDescriptionError,
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            //Update Animal Description
            InputField(
                text = animalDescriptionTextFieldValue,
                onTextChange = {
                    animalDescriptionTextFieldValue = it
                    animalDescriptionError = ""
                },
                inputPlaceholder = stringResource(R.string.description),
                editing = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (animalDescriptionError.isNotBlank()) {
                Text(
                    text = animalDescriptionError,
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Update Animal Category
            DropdownSelect(
                stringResource(R.string.species),
                animalCategoryDropdownValue,
                vm.getCategoryMap(animalCategoryList.value)
            ) {
                animalCategoryDropdownValue = it
                animalCategoryEditingState = true
            }

            if (animalCategoryError.isNotBlank()) {
                Text(
                    text = animalCategoryError,
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }

            // Update Animal Color
            DropdownSelect(
                stringResource(R.string.color_of_animal),
                animalColorDropdownValue,
                vm.getColorMap(animalColorList.value)
            ) {
                animalColorDropdownValue = it
                animalColorEditingState = true
            }

            // Update Animal Birthdate
            DatePicker(animalBirthdateValue) {
                animalBirthdateValue = it
                animalBirthdateError = ""
                animalBirthdateEditingState = true
            }

            // animal Weight
            InputField(
                text = animalWeightTextFieldValue,
                onTextChange = {
                    animalWeightTextFieldValue = it
                    animalWeightError = ""
                },
                inputPlaceholder = stringResource(R.string.weight),
                editing = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (animalWeightError.isNotBlank()) {
                Text(
                    text = animalWeightError,
                    color = Color.Red,
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Update Animal Gender
            Switch(animalGenderValue) {
                animalGenderValue = it
                animalGenderEditingState = true
            }

            Button(
                onClick = {
                    vm.updateAnimal(
                        animalNameTextFieldValue.text,
                        animalDescriptionTextFieldValue.text,
                        animalCategoryDropdownValue,
                        animalColorDropdownValue,
                        animalBirthdateValue,
                        animalWeightTextFieldValue.text.toFloat(),
                        animalGenderValue,
                    )
                }
            ) {
                Text(text = "Tier updaten ")
            }
        }
    } else {
        Text(text = "Kein Animal Gefunden")
    }
}

