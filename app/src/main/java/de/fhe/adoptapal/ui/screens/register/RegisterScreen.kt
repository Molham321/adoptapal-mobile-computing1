package de.fhe.adoptapal.ui.screens.register

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun RegisterScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
    ) {
        Spacer(modifier = Modifier.height(200.dp))
        Text(
            text =  "Willkommen bei AdoptAPal!",

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 0.dp),
            fontSize = 25.sp,
            color = colorResource(id = R.color.black),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )
        InputField(inputPlaceholder = "Name")
        InputField(inputPlaceholder = "Email-Adresse")
        InputField(inputPlaceholder = "Telefonnummer")
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            InputField(inputPlaceholder = "Passwort")
            InputField(inputPlaceholder = "Passwort wiederholen")
        }

    }
}