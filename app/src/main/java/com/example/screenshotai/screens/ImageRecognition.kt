package com.example.screenshotai.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@Composable
fun inputing()
{
    Column(Modifier.fillMaxSize() , verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally ){
        Text("Home Screen")
        IconButton(
            onClick = {},
            modifier = Modifier
        ) {
            Icon(imageVector = Icons.Default.Home , contentDescription = null)
        }
    }
}

fun recognize()
{
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

}