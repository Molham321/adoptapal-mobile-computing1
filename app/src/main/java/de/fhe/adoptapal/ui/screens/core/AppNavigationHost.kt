package de.fhe.adoptapal.ui.screens.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.fhe.adoptapal.ui.screens.addAnimal.AddAnimalScreen
import de.fhe.adoptapal.ui.screens.addAnimal.AddAnimalScreenViewModel
import de.fhe.adoptapal.ui.screens.animalDetail.DetailScreen
import de.fhe.adoptapal.ui.screens.animalDetail.DetailScreenViewModel
import de.fhe.adoptapal.ui.screens.home.HomeScreen
import de.fhe.adoptapal.ui.screens.home.HomeScreenViewModel
import de.fhe.adoptapal.ui.screens.home.SearchScreen
import de.fhe.adoptapal.ui.screens.login.LoginScreen
import de.fhe.adoptapal.ui.screens.login.LoginScreenViewModel
import de.fhe.adoptapal.ui.screens.map.MapScreen
import de.fhe.adoptapal.ui.screens.map.MapScreenViewModel
import de.fhe.adoptapal.ui.screens.profile.ProfileScreen
import de.fhe.adoptapal.ui.screens.profile.ProfileScreenViewModel
import de.fhe.adoptapal.ui.screens.register.RegisterScreen
import de.fhe.adoptapal.ui.screens.register.RegisterScreenViewModel
import de.fhe.adoptapal.ui.screens.settings.SettingsScreen
import de.fhe.adoptapal.ui.screens.settings.SettingsScreenViewModel
import de.fhe.adoptapal.ui.screens.updateAnimal.UpdateAnimalScreen
import de.fhe.adoptapal.ui.screens.updateAnimal.UpdateAnimalScreenViewModel
import de.fhe.adoptapal.ui.screens.userDetail.UserDetailScreen
import de.fhe.adoptapal.ui.screens.userDetail.UserDetailScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Composable function responsible for defining the navigation routes within the app.
 *
 * @param navigationManager The navigation manager responsible for handling navigation commands.
 * @param navController The navigation controller for managing navigation.
 * @param onNavigation Callback function for when navigation to a new screen occurs.
 * @param modifier The modifier to apply to the composable.
 */
@Composable
fun AppNavigationHost(
    navigationManager: NavigationManager,
    navController: NavHostController,
    onNavigation: (screen: Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    navigationManager.commands.collectAsState().value.also { command ->
        if (command.destination.isNotEmpty()) {
            if (command.destination == "go_back") {
                navController.popBackStack()
            } else if (RootScreens.any { it.route == command.destination }) {
                navController.navigate(command.destination) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                }
            } else {
                navController.navigate(command.destination)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            val vm: HomeScreenViewModel = koinViewModel()

            Screen.Home.prepareAppBarActions(vm)
            onNavigation(Screen.Home)
            HomeScreen(vm)
        }
        composable(
            Screen.Detail.route,
            Screen.Detail.navigationCommand(0).arguments
        ) { entry ->
            val animalId = entry.arguments?.getLong("animalId")
            val vm: DetailScreenViewModel = koinViewModel(parameters = { parametersOf(animalId) })

            Screen.Detail.prepareAppBarActions(LocalContext.current, vm)
            onNavigation(Screen.Detail)

            DetailScreen(vm)
        }

        composable(
            Screen.UserDetail.route,
            Screen.UserDetail.navigationCommand(0).arguments
        ) { entry ->
            val userId = entry.arguments?.getLong("userId")
            val vm: UserDetailScreenViewModel = koinViewModel(parameters = { parametersOf(userId) })

            Screen.UserDetail.prepareAppBarActions(LocalContext.current, vm)
            onNavigation(Screen.UserDetail)

            UserDetailScreen(vm)
        }
        composable(Screen.Map.route) {
            val vm: MapScreenViewModel = koinViewModel()
            Screen.Map.prepareAppBarActions(vm)
            onNavigation(Screen.Map)
            MapScreen(vm)
        }

        composable(Screen.Settings.route) {
            val vm: SettingsScreenViewModel = koinViewModel()
            Screen.Settings.prepareAppBarActions(vm)
            onNavigation(Screen.Settings)
            SettingsScreen(vm)
        }

        composable(Screen.AddAnimal.route) {
            val vm: AddAnimalScreenViewModel = koinViewModel()

            Screen.AddAnimal.prepareAppBarActions(vm)
            onNavigation(Screen.AddAnimal)
            AddAnimalScreen(vm)

        }

        composable(Screen.Profile.route) {
            val vm: ProfileScreenViewModel = koinViewModel()
            Screen.Profile.prepareAppBarActions(vm)
            onNavigation(Screen.Profile)
            ProfileScreen(vm)
        }

        composable(Screen.Search.route) {
            val vm: HomeScreenViewModel = koinViewModel()
            Screen.Search.prepareAppBarActions(vm)
            onNavigation(Screen.Search)
            SearchScreen(vm = vm, onFiltersApplied = {}) {
                // Reset the filters and show all animals
                vm.resetFiltersAndShowAllAnimals()
                vm.showFilterDialog = false // Close the dialog after resetting filters
            }
        }

        composable(Screen.Login.route) {
            val vm: LoginScreenViewModel = koinViewModel()
            Screen.Login.prepareAppBarActions(vm)
            onNavigation(Screen.Login)
            LoginScreen(vm)
        }

        composable(Screen.Register.route) {
            val vm: RegisterScreenViewModel = koinViewModel()
            Screen.Register.prepareAppBarActions(vm)
            onNavigation(Screen.Register)
            RegisterScreen(vm)
        }

        composable(
            Screen.UpdateAnimal.route,
            Screen.UpdateAnimal.navigationCommand(0).arguments
        ) { entry ->
            val animalId = entry.arguments?.getLong("animalId")
            val vm: UpdateAnimalScreenViewModel = koinViewModel(parameters = { parametersOf(animalId) })

            Screen.UpdateAnimal.prepareAppBarActions(LocalContext.current, vm)
            onNavigation(Screen.UpdateAnimal)

            UpdateAnimalScreen(vm)
        }
    }
}