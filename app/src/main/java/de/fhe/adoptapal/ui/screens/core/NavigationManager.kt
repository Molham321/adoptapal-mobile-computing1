package de.fhe.adoptapal.ui.screens.core

import androidx.navigation.NamedNavArgument
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Represents a navigation command with a destination and associated arguments.
 */
interface NavigationCommand {
    val destination: String
    val arguments: List<NamedNavArgument>
}

/**
 * A predefined empty navigation destination.
 */
val EmptyDestination = object : NavigationCommand {
    override val arguments = emptyList<NamedNavArgument>()
    override val destination = ""
}

/**
 * A predefined navigation command to navigate back.
 */
val GoBackDestination = object : NavigationCommand {
    override val arguments = emptyList<NamedNavArgument>()
    override val destination = "go_back"
}

/**
 * Manages navigation commands within the app by providing a [MutableStateFlow] of [NavigationCommand]s.
 */
class NavigationManager {
    var commands = MutableStateFlow(EmptyDestination)

    /**
     * Navigates to the specified [NavigationCommand].
     *
     * @param navCommand The navigation command to navigate to.
     */
    fun navigate(navCommand: NavigationCommand) {
        commands.value = navCommand
    }
}