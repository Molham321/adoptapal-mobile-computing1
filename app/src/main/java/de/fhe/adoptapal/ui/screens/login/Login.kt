package de.fhe.adoptapal.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.fhe.adoptapal.R

@Composable
fun Login(
    modifier: Modifier = Modifier,
    onItemPressed: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(160.dp))
        Text(
            text = "Willkommen zurück!",

            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 0.dp),
            fontSize = 25.sp,
            color = colorResource(id = R.color.black),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(20.dp))
        InputField(inputPlaceholder = "Email-Adresse")
        InputField(inputPlaceholder = "Passwort")
        Spacer(modifier = modifier.height(20.dp))
        Button(
            onClick = {},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Green
            ),
            modifier = modifier
                .width(250.dp)
                .padding(16.dp, 8.dp, 16.dp, 8.dp),

            ) {
            Text(text = "Login")
        }
        Button(
            onClick = {onItemPressed()},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            elevation = null,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp, 16.dp, 8.dp)

            ) {
            Text(
                text = "Noch kein Mitglied? zur Regisrierung",
                textDecoration = TextDecoration.Underline
            )
        }
    }
}