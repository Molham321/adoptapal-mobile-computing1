package de.fhe.adoptapal.ui.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(vm: LoginScreenViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Login(modifier = modifier) {
            vm.navigateToRegister()
        }
    }
}