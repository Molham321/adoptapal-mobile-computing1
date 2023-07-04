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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun Switch(
    genderValue: Boolean,
    editing: Boolean = false,
    onValueChange: (Boolean) -> Unit
) {
    // Fetching the Local Context
    val mContext = LocalContext.current

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Declaring a boolean value for storing checked state
        val mCheckedState = remember { mutableStateOf(false) }

        // Creating a Switch, when value changes,
        // it updates mCheckedState value
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Geschlecht:", modifier = Modifier.width(100.dp))
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
                Text(text = "männlich", modifier = Modifier.width(80.dp))
            } else {
                Text(text = "weiblich", modifier = Modifier.width(80.dp))
            }
        }

//        // Adding a Space of 100dp height
//        Spacer(modifier = Modifier.height(100.dp))
//
//        // Creating a Button to display mCheckedState
//        // value in a Toast when clicked
//        Button(onClick = {
//            if(!mCheckedState.value) {
//                Toast.makeText(mContext, "männlich", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(mContext, "weiblich", Toast.LENGTH_SHORT).show()
//            }
//        },
//            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)),
//        ) {
//            Text("Show Checked State", color = Color.White)
//        }
    }
}