package de.fhe.adoptapal.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Icon"
                        )
                    }
                },
                title = { Text("Home") },
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            )
            {
                AnimalCard()
                AnimalCard()
                AnimalCard()
                AnimalCard()
                AnimalCard()
                AnimalCard()
                AnimalCard()
                AnimalCard()
                AnimalCard()
                AnimalCard()
            }
        }
    )
}