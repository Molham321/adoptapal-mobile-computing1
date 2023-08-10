package de.fhe.adoptapal.ui.screens.addAnimal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R

@Composable
fun Switch(
    genderValue: Boolean,
    editing: Boolean = false,
    onValueChange: (Boolean) -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val mCheckedState = remember { mutableStateOf(false) }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = stringResource(id = R.string.gender), modifier = Modifier.width(100.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Switch(
                modifier = Modifier
                    .width(50.dp),
                checked = genderValue,
                onCheckedChange = {
                    mCheckedState.value = it
                    onValueChange(mCheckedState.value)
                })
            Spacer(modifier = Modifier.width(20.dp))
            if (genderValue) {
                Text(text = stringResource(id = R.string.male), modifier = Modifier.width(80.dp))
            } else {
                Text(text =  stringResource(id = R.string.female), modifier = Modifier.width(80.dp))
            }
        }
    }
}