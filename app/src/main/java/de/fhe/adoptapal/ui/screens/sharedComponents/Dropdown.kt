package de.fhe.adoptapal.ui.screens.sharedComponents

//@Composable
//fun Dropdown() {
//    var expanded by remember { mutableStateOf(false) }
//    val items = listOf("A", "B", "C", "D", "E", "F")
//    val disabledValue = "B"
//    var selectedIndex by remember { mutableStateOf(0) }
//    Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {
//        Text(items[selectedIndex],modifier = Modifier.fillMaxWidth().clickable(onClick = { expanded = true }).background(
//            Color.Gray))
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier.fillMaxWidth().background(
//                Color.Red)
//        ) {
//            items.forEachIndexed { index, s ->
//                DropdownMenuItem(onClick = {
//                    selectedIndex = index
//                    expanded = false
//                }) {
//                    val disabledText = if (s == disabledValue) {
//                        " (Disabled)"
//                    } else {
//                        ""
//                    }
//                    Text(text = s + disabledText)
//                }
//            }
//        }
//    }
//}