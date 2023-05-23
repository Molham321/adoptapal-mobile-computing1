package de.fhe.adoptapal.ui.screens.core

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument

val RootScreens = listOf(
    Screen.Home,
    Screen.Map,
    Screen.Settings
)

sealed class Screen(
    val title: String = "Title",
    val icon: ImageVector = Icons.Filled.Favorite,
    val route: String = ""
) {
    var appBarActions: @Composable RowScope.() -> Unit = {}
        protected set

    //open fun prepareAppBarActions(vararg values: Any) {}

    open fun navigationCommand(vararg value: Any) = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = route
    }

   // Actual Screen Definitions

    object Home : Screen(
        title = "Home",
        icon = Icons.Filled.Home,
        route = "Home"
    )

    object Detail : Screen(
        title = "Detail",
        icon = Icons.Filled.ArrowBack,
        route = "Detail/{animalId}"
    )

    object Map : Screen(
        title = "Map",
        icon = Icons.Filled.Place,
        route = "Map"
    )

    object Settings : Screen(
        title = "Settings",
        icon = Icons.Filled.Settings,
        route = "Settings"
    )

    object Input : Screen(
        title = "Input",
        icon = Icons.Filled.Create,
        route = "Input"
    )
    object Login : Screen(
        title = "Login",
        icon = Icons.Filled.Menu,
        route = "Login"
    )
    object Register : Screen(
        title = "Register",
        icon = Icons.Filled.Menu,
        route = "Register"
    )

    object Undefined : Screen(
        title = "Undefined",
        icon = Icons.Filled.Warning,
        route = ""
    )
}