package de.fhe.adoptapal.ui.screens.addAnimal

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Slider
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.ui.screens.core.LocalScaffoldState
import org.koin.androidx.compose.getViewModel


@Composable
fun AddAnimalScreen() {
   // val vm = getViewModel<AddAnimalViewModel>()

    // val saveState by remember(vm) { vm.saveFeedbackFlow }
    //    .collectAsState(AsyncOperation.undefined())

    var animalNameTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var animalNameEditingState by remember { mutableStateOf(false) }
    var animalDescriptionTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var animalDescriptionEditingState by remember { mutableStateOf(false) }

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

        InputField(animalNameTextFieldValue, animalNameEditingState, "Name des Tieres") {
            animalNameTextFieldValue = it
            animalNameEditingState = true
        }

        Spacer(Modifier.height(30.dp))

        InputField(animalDescriptionTextFieldValue, animalDescriptionEditingState, "Beschreibung des Tieres") {
            animalDescriptionTextFieldValue = it
            animalDescriptionEditingState = true
        }

        Spacer(Modifier.height(30.dp))

        DropdownSelect("Tierrasse", arrayOf("Hund", "Katze", "Nagetier", "Reptil", "Vogel"))

        Spacer(Modifier.height(30.dp))

        DropdownSelect("Fellfarbe", arrayOf("schwarz", "weiß", "blond", "orange", "braun", "gemustert", "kein Fell"))

        Spacer(Modifier.height(30.dp))

        Button(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            onClick = {
                println("$animalNameTextFieldValue")
                Toast.makeText(contextForToast, "${animalNameTextFieldValue.text} ${animalDescriptionTextFieldValue.text}", Toast.LENGTH_SHORT)
                    .show()
                // vm.addAnimal(animalNameTextFieldValue.text)
                animalNameTextFieldValue = TextFieldValue("") // Clear Form
                animalDescriptionTextFieldValue = TextFieldValue("")
                animalNameEditingState = false // To hide keyboard
                animalDescriptionEditingState = false
            }
        ) {
            Text(text = "Save")
        }

        Button(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
            onClick = {
//                vm.navigateToUserList()
            }
        ) {
            Text(text = "Go Back")
        }
    }
}
