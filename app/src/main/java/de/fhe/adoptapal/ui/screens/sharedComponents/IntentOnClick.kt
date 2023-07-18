package de.fhe.adoptapal.ui.screens.sharedComponents

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat

/**
 * send an intent to an email app
 */
fun composeEmail(context: Context, emailAddress: String, subject: String, message: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_EMAIL, emailAddress)
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, message)
    }
    ContextCompat.startActivity(context, Intent.createChooser(intent, "Send an email..."), null)
}

/**
 * send an intent to a phone app
 */
fun composeCall(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_CALL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    ContextCompat.startActivity(context, Intent.createChooser(intent, "Call a number..."), null)
}