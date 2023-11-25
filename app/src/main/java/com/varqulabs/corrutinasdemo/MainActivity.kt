package com.varqulabs.corrutinasdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import com.varqulabs.corrutinasdemo.data.local.FlowRepositoryImpl
import com.varqulabs.corrutinasdemo.ui.theme.CorrutinasDemoTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CorrutinasDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SimpleComposable()
                }
            }
        }
    }
}

@Composable
fun SimpleComposable(

) {
     var num by rememberSaveable {
         mutableStateOf(0)
     }

    Column {
        Text("El numero es $num")
        Divider()
        Counter(
            nameButton = "Incrementar",
            onClick = {
                num++
            }
        )
        Counter(
            nameButton = "Decrementar",
            onClick = {
                num--
            }
        )
    }
}

@Composable
fun Counter(
    nameButton: String,
    onClick: () -> Unit
) {
    Button(onClick = { onClick() }) {
        Text(nameButton)
    }



}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CorrutinasDemoTheme {
        Greeting("Android")
    }
}