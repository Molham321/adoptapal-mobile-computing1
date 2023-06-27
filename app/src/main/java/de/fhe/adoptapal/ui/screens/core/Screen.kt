package de.fhe.adoptapal.ui.screens.core

import android.content.Context
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import de.fhe.adoptapal.ui.screens.animalDetail.DetailScreenViewModel
import de.fhe.adoptapal.ui.screens.home.HomeScreenViewModel
import de.fhe.adoptapal.ui.screens.login.LoginScreenViewModel
import de.fhe.adoptapal.ui.screens.profile.ProfileScreenViewModel
import de.fhe.adoptapal.ui.screens.register.RegisterScreenViewModel
import de.fhe.adoptapal.ui.screens.settings.SettingsScreenVieModel

val RootScreens = listOf(
    Screen.Map,
    Screen.Home,
    Screen.Profile
)

sealed class Screen(
    val title: String = "Title",
    val icon: ImageVector = Icons.Filled.Favorite,
    val route: String = "",
    val hasReturn: Boolean = true
) {
    var appBarActions: @Composable RowScope.() -> Unit = {}
        protected set

    open fun prepareAppBarActions(vararg values: Any) {}

    open fun navigationCommand(vararg value: Any) = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = route
    }

    // Actual Screen Definitions

    object Home : Screen(
        title = "Home",
        icon = Icons.Filled.Home,
        route = "Home",
        hasReturn = false
    ) {
        override fun prepareAppBarActions(vararg values: Any) {
            if (values[0] !is HomeScreenViewModel)
                error("First Parameter must be of type *HomeScreenViewModel*")
            val viewModel = values[0] as HomeScreenViewModel

            appBarActions = {
                IconButton(onClick = { viewModel.navigateToAddAnimal() }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
                IconButton(onClick = { viewModel.navigateToSearch() }
                ) {
                    Icon(Icons.Filled.Search, contentDescription = null)
                }
                IconButton(onClick = { viewModel.navigateToLogin() }) {
                    Icon(Icons.Filled.Lock, contentDescription = "Login Icon")
                }
            }
        }
    }

    object Detail : Screen(
        title = "Detail",
        icon = Icons.Filled.ArrowBack,
        route = "Detail/{animalId}"
    ) {
        override fun prepareAppBarActions(vararg values: Any) {
            if (values[0] !is Context)
                error("First Parameter must be of type *Context*")
            if (values[1] !is DetailScreenViewModel)
                error("Second Parameter must be of type *DetailScreenViewModel")

            appBarActions = {}
        }

        override fun navigationCommand(vararg value: Any) = object : NavigationCommand {

            override val arguments = listOf(
                navArgument("animalId") {
                    type = NavType.LongType
                }
            )
            override val destination = "Detail/${value[0]}"
        }
    }

    object Map : Screen(
        title = "Map",
        icon = Icons.Filled.Place,
        route = "Map",
        hasReturn = false
    )

    object Settings : Screen(
        title = "Settings",
        icon = Icons.Filled.Settings,
        route = "Settings"
    ) {
        override fun prepareAppBarActions(vararg values: Any) {
            if (values[0] !is SettingsScreenVieModel)
                error("First Parameter must be of type *SettingsScreenVieModel*")
            val viewModel = values[0] as SettingsScreenVieModel

            appBarActions = {}
        }
    }
    object Profile : Screen(
        title = "Profile",
        icon = Icons.Filled.Person,
        route = "Profile",
        hasReturn = false
    ) {
        override fun prepareAppBarActions(vararg values: Any) {
            if (values[0] !is ProfileScreenViewModel)
                error("First Parameter must be of type *ProfileScreenViewModel*")
            val viewModel = values[0] as ProfileScreenViewModel

            appBarActions = {
                IconButton(onClick = { viewModel.navigateToSettings() }
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = null)
                }
            }
        }
    }

    object Input : Screen(
        title = "Input",
        icon = Icons.Filled.Create,
        route = "Input"
    )

    object Search : Screen(
        title = "Search",
        icon = Icons.Filled.Search,
        route = "Search"
    )

    object Login : Screen(
        title = "Login",
        icon = Icons.Filled.Menu,
        route = "Login"
    ) {
        override fun prepareAppBarActions(vararg values: Any) {
            if (values[0] !is LoginScreenViewModel)
                error("First Parameter must be of type *LoginScreenViewModel*")
            val viewModel = values[0] as LoginScreenViewModel

            appBarActions = {}
        }
    }

    object Register : Screen(
        title = "Register",
        icon = Icons.Filled.Menu,
        route = "Register"
    ) {
        override fun prepareAppBarActions(vararg values: Any) {
            if (values[0] !is RegisterScreenViewModel)
                error("First Parameter must be of type *RegisterScreenViewModel*")
            val viewModel = values[0] as RegisterScreenViewModel

            appBarActions = {}
        }
    }

    object Undefined : Screen(
        title = "Undefined",
        icon = Icons.Filled.Warning,
        route = ""
    )
}