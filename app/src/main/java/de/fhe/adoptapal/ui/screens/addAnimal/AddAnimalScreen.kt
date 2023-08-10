package de.fhe.adoptapal.ui.screens.addAnimal

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import de.fhe.adoptapal.R
import de.fhe.adoptapal.ui.screens.util.FileSystemHandler
import de.fhe.adoptapal.ui.theme.LightModeSecondary

@Composable
fun AddAnimalScreen(vm: AddAnimalScreenViewModel, modifier: Modifier = Modifier) {

    val animalCategoryList = remember { vm.animalCategoryList }
    val animalColorList = remember { vm.animalColorList }

    val context = LocalContext.current


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
    var animalGenderValue by remember { mutableStateOf(false) }
    var animalGenderEditingState by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }


    val contextForToast = LocalContext.current.applicationContext

    var birthdateError by remember { mutableStateOf("") }
    var weightError by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        Spacer(Modifier.height(20.dp))

        val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                Log.i("ImageUpload", uri.toString())
                selectedImageUri = uri
            })


        Text(
            text = stringResource(R.string.upload_a_new_animal),

            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 0.dp),
            fontSize = 25.sp,
            color = colorResource(id = R.color.black),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )


        Spacer(Modifier.height(20.dp))

        InputField(
            animalNameTextFieldValue,
            animalNameEditingState,
            stringResource(R.string.name_of_the_animal)
        ) {
            animalNameTextFieldValue = it
            animalNameEditingState = true
        }

        Spacer(Modifier.height(15.dp))

        InputField(
            animalDescriptionTextFieldValue,
            animalDescriptionEditingState,
            stringResource(R.string.description_of_the_animal)
        ) {
            animalDescriptionTextFieldValue = it
            animalDescriptionEditingState = true
        }

        Spacer(Modifier.height(15.dp))

        DropdownSelect(
            stringResource(R.string.species),
            animalCategoryDropdownValue,
            vm.getCategoryMap(animalCategoryList.value)
        ) {
            animalCategoryDropdownValue = it
            animalCategoryEditingState = true
        }

        Spacer(Modifier.height(15.dp))

        DropdownSelect(
            stringResource(R.string.color_of_animal),
            animalColorDropdownValue,
            vm.getColorMap(animalColorList.value)
        ) {
            animalColorDropdownValue = it
            animalColorEditingState = true
        }

        Spacer(Modifier.height(15.dp))

        DatePicker(animalBirthdateValue) {
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

        InputField(
            animalWeightTextFieldValue, animalWeightEditingState,
            stringResource(id = R.string.weight)
        ) {
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

        Switch(animalGenderValue) {
            animalGenderValue = it
            animalGenderEditingState = true
        }

        Spacer(Modifier.height(15.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                        Text(text = stringResource(R.string.select_image))
                    }
                }

                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .height(50.dp)
                        .width(70.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }



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
                Text(text = stringResource(R.string.save_animal))
            }
        } else {
            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClick = {
                    var imageUri: String? = null
                    if (selectedImageUri != null) {
                        val file = FileSystemHandler.createImageFile(context)
                        val imageStream =
                            context.contentResolver.openInputStream(selectedImageUri!!)
                        val imageBmp = BitmapFactory.decodeStream(imageStream)

                        FileSystemHandler.saveFile(file!!, imageBmp)

                        imageUri = file.absolutePath
                    }


                    val isBirthdateValid = vm.validateBirthdate(animalBirthdateValue)
                    val isWeightValid = vm.validateWeight(animalWeightTextFieldValue.text)

                    if (isBirthdateValid && isWeightValid) {
                        vm.addAnimal(
                            animalNameTextFieldValue.text,
                            animalDescriptionTextFieldValue.text,
                            animalCategoryDropdownValue,
                            animalColorDropdownValue,
                            animalBirthdateValue,
                            animalWeightTextFieldValue.text.toFloat(),
                            animalGenderValue,
                            imageUri
                        )

                        Toast.makeText(
                            contextForToast,
                            "Tier ${animalNameTextFieldValue.text} wurde gespeichert",
                            Toast.LENGTH_LONG
                        )
                            .show()

                        animalNameTextFieldValue = TextFieldValue("")
                        animalDescriptionTextFieldValue = TextFieldValue("")
                        animalCategoryDropdownValue = 0
                        animalColorDropdownValue = 0
                        animalBirthdateValue = ""
                        animalWeightTextFieldValue = TextFieldValue("")
                        animalGenderValue = false

                        animalNameEditingState = false
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
                Text(text = stringResource(id = R.string.save_animal))
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
            Text(text = stringResource(R.string.back))

        }
    }
}



