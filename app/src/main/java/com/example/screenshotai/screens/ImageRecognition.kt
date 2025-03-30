package com.example.screenshotai.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun inputing()
{
    Column(Modifier.fillMaxSize() , verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally ){
        Text("Home Screen")

        Takeimage()

    }

}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Takeimage()
{
    val context = LocalContext.current
    var selecteduri by remember {
        mutableStateOf<Uri?>(null)
    }
    var selectedurilist by remember {
        mutableStateOf<List<Uri?>>(emptyList())
    }
    val singleimagepicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult ={ uri->
            selecteduri=uri
            if(uri==null)
            {
                Toast.makeText(context,"NO image",Toast.LENGTH_SHORT)
            }
        }
    )
    val multiimagepicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = {multiuri->
            selectedurilist=multiuri
        }
    )
    IconButton(
        onClick = {
            singleimagepicker.launch("image/*")
        }
        ,
        modifier = Modifier
    ) {
        Icon(imageVector = Icons.Default.Home , contentDescription = null)

    }
    selecteduri.let { uri->
        recognizeTextFromImage(
            context, uri,

        )
    }
}
@RequiresApi(Build.VERSION_CODES.P)
fun uriToBitmap(context: Context, uri: Uri?): Bitmap? {
    if (uri == null) return null  // Handle null URI early

    return try {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
@RequiresApi(Build.VERSION_CODES.P)
fun recognizeTextFromImage(context: Context, uri: Uri?) {
    if (uri == null) {
        Log.d("TextRecognition", "URI is null, cannot process image")
        return
    }

    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val bitmap = uriToBitmap(context, uri)

    if (bitmap != null) {
        val image = InputImage.fromBitmap(bitmap, 0)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                Log.d("TextRecognition", "Extracted Text: ${visionText.text}")
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                Log.d("TextRecognition", "Text recognition failed: ${e.message}")
            }
    } else {
        Log.e("TextRecognition", "Bitmap is null, failed to decode image")
    }
}
