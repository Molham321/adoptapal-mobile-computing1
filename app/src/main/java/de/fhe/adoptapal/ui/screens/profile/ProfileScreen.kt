package de.fhe.adoptapal.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R
import de.fhe.adoptapal.domain.AsyncOperation
import de.fhe.adoptapal.domain.AsyncOperationState
import de.fhe.adoptapal.domain.User
import de.fhe.adoptapal.ui.screens.core.LocalScaffoldState
import de.fhe.adoptapal.ui.screens.login.LoginScreenViewModel

@Composable
fun ProfileScreen(vm: ProfileScreenViewModel, modifier: Modifier = Modifier) {

    vm.reload()

    val user = remember{vm.user}

    if( user.value != null)
    {
        Column() {
            Profile(user.value!!)
        }

    } else {
        Column() {
            Text(
                text = "Du bist nicht angemeldet! Melde dich erstmal an.",
                modifier = Modifier.padding(16.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}