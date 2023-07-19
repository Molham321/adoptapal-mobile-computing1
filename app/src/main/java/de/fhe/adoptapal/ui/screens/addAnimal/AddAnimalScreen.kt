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
import de.fhe.adoptapal.ui.theme.LightModeSecondary


//@Preview
@Composable
fun AddAnimalScreen(vm: AddAnimalScreenViewModel, modifier: Modifier = Modifier) {
//   val vm = getViewModel<AddAnimalViewModel>()
//
//     val saveState by remember(vm) { vm.saveFeedbackFlow }
//        .collectAsState(AsyncOperation.undefined())

    // val dp0p = remember{ vm.dbOp }
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

//    LaunchedEffect(saveState) {
//        if (saveState.status != AsyncOperationState.UNDEFINED) {
//            scaffoldState.snackbarHostState.showSnackbar(
//                message = "${saveState.message}...",
//                duration = SnackbarDuration.Short
//            )
//        }
//    }

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

//        animalCategoryList.value.forEach {
//            Text(text = it.name)
//        }
//        Text(text = vm.getColorArray(animalColorList.value).joinToString())

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
            animalBirthdateEditingState = true
        }

        Spacer(Modifier.height(15.dp))

        InputField(animalWeightTextFieldValue, animalWeightEditingState, "Gewicht") {
            animalWeightTextFieldValue = it
            animalWeightEditingState = true
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
                }
            ) {
                Text(text = "Tier speichern")
            }
        }



        Button(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = LightModeSecondary),
            onClick = {
                vm.navigateToUserList()
            }
        ) {
            Text(text = "zurück")
        }
    }
}
