import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.google.mlkit.nl.entityextraction.EntityExtraction
import com.google.mlkit.nl.entityextraction.EntityExtractionParams
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException
import kotlin.math.sin

@Composable
fun inputing()
{
    val context = LocalContext.current
    var selecteduri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val singleimagepicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(), onResult = {uri->
        selecteduri=uri
        if(uri==null)
        {
            Toast.makeText(context, "No image selected", Toast.LENGTH_SHORT).show()

        }
    })
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(all = 16.dp) ,
       )
    {
        Box(modifier = Modifier.fillMaxSize()){   Example(onClick = { singleimagepicker.launch("image/*") } ,
            Modifier
                .align(Alignment.BottomEnd)
                .padding(32.dp)
        )
            selecteduri?.let {
                uri->
                LaunchedEffect(uri){ recognition(context, uri) }
            AsyncImage(model = (uri),null)
        }
        }


    }
}
@Composable
fun Example(onClick: () -> Unit,modifier: Modifier) {
    LargeFloatingActionButton(
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier,
        //interactionSource = TODO(),
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}
fun recognition(context: Context,uri: Uri)
{
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    val image: InputImage
    try {
        image = InputImage.fromFilePath(context, uri)

    } catch (e: IOException) {
        e.printStackTrace()
        return
    }
    val result = recognizer.process(image)
        .addOnSuccessListener { visionText ->
            Log.d("ttyl",visionText.text)
        }
        .addOnFailureListener { e ->
            Log.d("ttyl",e.message.toString())
        }

}