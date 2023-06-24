package de.fhe.adoptapal.ui.screens.core

import android.content.Context
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import de.fhe.adoptapal.ui.screens.addAnimal.AddAnimalViewModel
import de.fhe.adoptapal.ui.screens.animalDetail.DetailScreenViewModel
import de.fhe.adoptapal.ui.screens.home.HomeScreenViewModel
import de.fhe.adoptapal.ui.screens.login.LoginScreenViewModel
import de.fhe.adoptapal.ui.screens.register.RegisterScreenViewModel

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

    open fun prepareAppBarActions(vararg values: Any) {}

    open fun navigationCommand(vararg value: Any) = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = route
    }

    // Actual Screen Definitions

    object Home : Screen(
        title = "Home",
        icon = Icons.Filled.Home,
        route = "Home"
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
        route = "Map"
    )

    object Settings : Screen(
        title = "Settings",
        icon = Icons.Filled.Settings,
        route = "Settings"
    )

    object AddAnimal : Screen(
        title = "AddAnimal",
        icon = Icons.Filled.Create,
        route = "AddAnimal"
    )
//    {
//        override fun prepareAppBarActions(vararg values: Any) {
//            if (values[0] !is AddAnimalViewModel)
//                error("First Parameter must be of type *AddAnimalViewModel*")
//            val viewModel = values[0] as AddAnimalViewModel
//
//            appBarActions = {}
//        }
//    }

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
    )

    object Undefined : Screen(
        title = "Undefined",
        icon = Icons.Filled.Warning,
        route = ""
    )
}