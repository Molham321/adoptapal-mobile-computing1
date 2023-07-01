package de.fhe.adoptapal.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(vm: SettingsScreenViewModel, modifier: Modifier = Modifier) {

    val user = remember{vm.user}

    if(user.value != null) {
        Column() {
            Settings(user.value!!) {
                vm.updateUser(it)
            }
        }
    } else {
        Column() {
            Text(
                text = "Du bist nicht angemeldet! Melde dich erstmal an.",
                modifier = modifier.padding(16.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
        }
    }

}