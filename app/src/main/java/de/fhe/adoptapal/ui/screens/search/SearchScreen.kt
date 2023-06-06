package de.fhe.adoptapal.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import de.fhe.adoptapal.ui.screens.home.AnimalList
import de.fhe.adoptapal.ui.screens.home.FilterBar
import de.fhe.adoptapal.ui.screens.home.SearchBar
import de.fhe.adoptapal.ui.screens.util.FullscreenPlaceholderView

@Composable
fun SearchScreen() {
    Column() {
        Filter(onSubmit = {})
    }
}