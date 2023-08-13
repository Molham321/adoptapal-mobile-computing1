package de.fhe.adoptapal.ui.screens.updateAnimal

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
import de.fhe.adoptapal.ui.screens.sharedComponents.InputField
import de.fhe.adoptapal.ui.theme.LightModeSecondary

@Composable
fun UpdateAnimalScreen(vm: UpdateAnimalScreenViewModel, modifier: Modifier = Modifier) {

    val animal = remember { vm.animal }

    if (animal.value != null) {
        var name by remember { mutableStateOf(TextFieldValue(animal.value!!.name)) }

        var nameError by remember { mutableStateOf("") }

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

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    animal.value!!.name = name.text
                    vm.updateAnimal(animal.value!!)
                }
            ) {
                Text(text = "Tier updaten ")
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
    } else {
        Text(text = "Kein Animal Gefunden")
    }
}

