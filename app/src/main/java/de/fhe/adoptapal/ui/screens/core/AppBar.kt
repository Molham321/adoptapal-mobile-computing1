package de.fhe.adoptapal.ui.screens.core

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AppBar(screen: Screen, navController: NavHostController? = null) {
    TopAppBar(
        title = {
            Text(text = screen.title)
        },
        navigationIcon = {
       if(screen.hasReturn) {
               IconButton(onClick = { navController?.popBackStack() }) {
                   Icon(
                       Icons.Filled.ArrowBack,
                       screen.title,
                       modifier = Modifier.padding(horizontal = 12.dp)
                   )
               }
           } else {
               Icon(
                   screen.icon,
                   screen.title,
                   modifier = Modifier.padding(horizontal = 12.dp)
               )
           }
        },
        actions = screen.appBarActions
    )
}

@Preview()
@Composable
fun HomeAppBar() {
    AppBar(screen = Screen.Home)
}

@Preview()
@Composable
fun DetailsAppBar() {
    AppBar(screen = Screen.Detail)
}

@Preview()
@Composable
fun MapAppBar() {
    AppBar(screen = Screen.Map)
}

@Preview()
@Composable
fun SettingsAppBar() {
    AppBar(screen = Screen.Settings)
}
@Preview()
@Composable
fun ProfileAppBar() {
    AppBar(screen = Screen.Profile)
}

@Preview()
@Composable
fun LoginAppBar() {
    AppBar(screen = Screen.Login)
}

@Preview()
@Composable
fun RegisterAppBar() {
    AppBar(screen = Screen.Register)
}

