package de.fhe.adoptapal.ui.screens.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.fhe.adoptapal.ui.screens.animalDetail.DetailScreen
import de.fhe.adoptapal.ui.screens.home.HomeScreen
import de.fhe.adoptapal.ui.screens.addAnimal.InputScreen
import de.fhe.adoptapal.ui.screens.animalDetail.DetailScreenViewModel
import de.fhe.adoptapal.ui.screens.home.HomeScreenViewModel
import de.fhe.adoptapal.ui.screens.login.LoginScreen
import de.fhe.adoptapal.ui.screens.map.MapScreen
import de.fhe.adoptapal.ui.screens.register.RegisterScreen
import de.fhe.adoptapal.ui.screens.search.SearchScreen
import de.fhe.adoptapal.ui.screens.settings.SettingsScreen
import org.koin.androidx.compose.getKoin
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigationHost(
    navigationManager: NavigationManager,
    onNavigation: (screen: Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    navigationManager.commands.collectAsState().value.also { command ->
        if (command.destination.isNotEmpty())
        // Special go back destination
            if (command.destination == "go_back")
                navController.popBackStack()
            // Destination is a Root Screen, we clean up the back stack
            else if(RootScreens.any { it.route == command.destination }) {
                navController.navigate(command.destination) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            // Any other destination - just navigate there
            else
                navController.navigate(command.destination)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
           val vm by getKoin().inject<HomeScreenViewModel>()

            Screen.Home.prepareAppBarActions(vm)
            onNavigation(Screen.Home)
            HomeScreen(vm)
        }
        composable(
            Screen.Detail.route,
            Screen.Detail.navigationCommand(0).arguments
        ) { entry ->
            val animalId = entry.arguments?.getInt("animalId")
            val vm by getKoin().inject<DetailScreenViewModel>(parameters = { parametersOf(animalId) })

            Screen.Detail.prepareAppBarActions(LocalContext.current, vm)
            onNavigation(Screen.Detail)

            DetailScreen(vm)
        }
        composable(Screen.Map.route) {
            onNavigation(Screen.Map)
            MapScreen()
        }
        composable(Screen.Settings.route) {
            onNavigation(Screen.Settings)
            SettingsScreen()
        }
        composable(Screen.Input.route) {
            onNavigation(Screen.Input)
            InputScreen()
        }
        composable(Screen.Search.route) {
            onNavigation(Screen.Search)
            SearchScreen()
        }
        composable(Screen.Login.route) {
            onNavigation(Screen.Login)
            LoginScreen()
        }
        composable(Screen.Register.route) {
            onNavigation(Screen.Register)
            RegisterScreen()
        }
    }
}