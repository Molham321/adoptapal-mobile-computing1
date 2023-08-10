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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import de.fhe.adoptapal.R
import de.fhe.adoptapal.ui.screens.addAnimal.AddAnimalScreenViewModel
import de.fhe.adoptapal.ui.screens.animalDetail.DetailScreenViewModel
import de.fhe.adoptapal.ui.screens.home.HomeScreenViewModel
import de.fhe.adoptapal.ui.screens.login.LoginScreenViewModel
import de.fhe.adoptapal.ui.screens.profile.ProfileScreenViewModel
import de.fhe.adoptapal.ui.screens.register.RegisterScreenViewModel
import de.fhe.adoptapal.ui.screens.settings.SettingsScreenViewModel
import de.fhe.adoptapal.ui.screens.userDetail.UserDetailScreenViewModel

val RootScreens = listOf(
    Screen.Map,
    Screen.Home,
    Screen.Profile
)

/**
 * Represents the possible screens in the application.
 *
 * @property title The title to be displayed in the app bar.
 * @property icon The icon associated with the screen.
 * @property route The navigation route for the screen.
 * @property hasReturn Indicates whether the screen has a return functionality.
 */
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

    /**
     * Represents the Home screen.
     */
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
                if (viewModel.user.value != null) {
                    IconButton(onClick = { viewModel.navigateToAddAnimal() }
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null)
                    }
                }
                IconButton(onClick = { viewModel.openFilterDialog() }
                ) {
                    Icon(Icons.Filled.Search, contentDescription = null)
                }
                if (viewModel.user.value == null) {
                    IconButton(onClick = { viewModel.navigateToLogin() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_login),
                            contentDescription = "Login Icon"
                        )
                    }
                } else {
                    IconButton(onClick = { viewModel.logout() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_logout_24),
                            contentDescription = "Login Icon"
                        )
                    }
                }
            }
        }

        override fun navigationCommand(vararg value: Any) = object : NavigationCommand {
            override val destination = "Home"
            override val arguments: List<NamedNavArgument>
                get() = TODO("Not yet implemented")
        }
    }

    /**
     * Represents the Detail screen.
     */
    object Detail : Screen(
        title = "Details",
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

    /**
     * Represents the Map screen.
     */
    object Map : Screen(
        title = "Karte",
        icon = Icons.Filled.Place,
        route = "Map",
        hasReturn = false
    )

    object Settings : Screen(
        title = "Einstellungen",
        icon = Icons.Filled.Settings,
        route = "Settings"
    ) {
        override fun prepareAppBarActions(vararg values: Any) {
            if (values[0] !is SettingsScreenViewModel)
                error("First Parameter must be of type *SettingsScreenVieModel*")
        }
    }

    object Profile : Screen(
        title = "Profil",
        icon = Icons.Filled.Person,
        route = "Profile",
        hasReturn = false
    ) {

        override fun prepareAppBarActions(vararg values: Any) {
            if (values[0] !is ProfileScreenViewModel)
                error("First Parameter must be of type *ProfileScreenViewModel*")
            val viewModel = values[0] as ProfileScreenViewModel

            appBarActions = {
                if (viewModel.user.value != null) {
                    IconButton(onClick = { viewModel.navigateToSettings() }
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = null)
                    }
                }
            }
        }
    }

    object AddAnimal : Screen(
        title = "Tier hinzufügen",
        icon = Icons.Filled.Create,
        route = "AddAnimal"
    ) {
        override fun prepareAppBarActions(vararg values: Any) {
            if (values[0] !is AddAnimalScreenViewModel)
                error("First Parameter must be of type *AddAnimalViewModel*")
        }
    }

    object Search : Screen(
        title = "Filter",
        icon = Icons.Filled.Search,
        route = "Search"
    ) {
        override fun prepareAppBarActions(vararg values: Any) {
            if (values[0] !is HomeScreenViewModel)
                error("First Parameter must be of type *HomeScreenViewModel*")

            appBarActions = {}
        }
    }

    object Login : Screen(
        title = "Anmeldung",
        icon = Icons.Filled.Menu,
        route = "Login"
    ) {
        override fun prepareAppBarActions(vararg values: Any) {
            if (values[0] !is LoginScreenViewModel)
                error("First Parameter must be of type *LoginScreenViewModel*")
        }
    }

    object Register : Screen(
        title = "Registrierung",
        icon = Icons.Filled.Menu,
        route = "Register"
    ) {
        override fun prepareAppBarActions(vararg values: Any) {
            if (values[0] !is RegisterScreenViewModel)
                error("First Parameter must be of type *RegisterScreenViewModel*")
        }
    }

    object UserDetail : Screen(
        title = "Nutzer Details",
        icon = Icons.Filled.ArrowBack,
        route = "UserDetail/{userId}"
    ) {
        override fun prepareAppBarActions(vararg values: Any) {
            if (values[0] !is Context)
                error("First Parameter must be of type *Context*")
            if (values[1] !is UserDetailScreenViewModel)
                error("Second Parameter must be of type *UserDetailScreenViewModel")

            appBarActions = {}
        }

        override fun navigationCommand(vararg value: Any) = object : NavigationCommand {

            override val arguments = listOf(
                navArgument("userId") {
                    type = NavType.LongType
                }
            )
            override val destination = "UserDetail/${value[0]}"
        }
    }

    /**
     * Represents the Undefined screen.
     */
    object Undefined : Screen(
        title = "Undefined",
        icon = Icons.Filled.Warning,
        route = ""
    )
}