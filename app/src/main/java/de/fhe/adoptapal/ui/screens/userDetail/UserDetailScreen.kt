package de.fhe.adoptapal.ui.screens.userDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import de.fhe.adoptapal.R

@Composable
fun UserDetailScreen(vm: UserDetailScreenViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
            .padding(16.dp, 16.dp, 16.dp, 16.dp)
    ) {

        if (vm.user.value != null) {
            UserInfo(
                user = vm.user.value!!,
                modifier = modifier
            )
        }
    }
}