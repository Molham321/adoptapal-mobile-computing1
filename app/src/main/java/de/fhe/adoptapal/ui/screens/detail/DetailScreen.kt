package de.fhe.adoptapal.ui.screens.detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailScreen() {
    
    Text(text = "Detail")

}


//    Scaffold(
//        topBar = {
//            TopAppBar(
//                navigationIcon = {
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBack,
//                            contentDescription = null,
//                            modifier = Modifier.size(24.dp, 24.dp)
//                        )
//                    }
//                },
//                title = { Text("Details") },
//            )
//        },
//
//        content = {
//            Details()
//        }
//    )