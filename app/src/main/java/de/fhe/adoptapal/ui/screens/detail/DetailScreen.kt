package de.fhe.adoptapal.ui.screens.detail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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