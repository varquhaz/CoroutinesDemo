package com.varqulabs.corrutinasdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import com.varqulabs.corrutinasdemo.data.local.FlowRepositoryImpl
import com.varqulabs.corrutinasdemo.ui.theme.CorrutinasDemoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.system.measureTimeMillis

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
                    var text1 by remember{
                        mutableStateOf("First Launch")
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LaunchedEffect(key1 = Unit){
                            val result = withContext(Dispatchers.IO){
                                longTask()
                            }
                            text1 = result
                        }
                        Text(text1)

                    }
                }
            }
        }
    }
}

suspend fun longTask(): String{
    delay(6000)
    return "Result from longTask"

}

@Composable
fun MyScr() {
    var isLoading by remember {
        mutableStateOf(false)
    }

    var text by remember{
        mutableStateOf("No data")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(isLoading){
            CircularProgressIndicator()
        }else{
            Text(text)
        }
        Button(
            onClick = {
                isLoading = true
                        //This executes in Other Thread
                        CoroutineScope(Dispatchers.Main).launch {
                            val time = measureTimeMillis {
                                delay(2000)
                                withContext(Dispatchers.IO){
                                    text = "Loaded data Succesfully in Main w/IO"
                                    isLoading = false
                                }
                            }
                            println(text)
                            println("Elapsed time: $time Main/IO")
                            println("Finished at ${SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())}")
                        }
                        //This in other Thread
                        CoroutineScope(Dispatchers.Main).launch{
                            val time2 = measureTimeMillis {
                                delay(2000)
                                println()
                                withContext(Dispatchers.Main){
                                    text = "Loaded Data Succesfully in Main w/Main"
                                    isLoading = false
                                }
                            }
                            println(text)
                            println("Elapsed time: $time2 Main/Main")
                            println("Finished at ${SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())}")
                        }
                //After that two Coroutines Finished at the same time too, dif(milis < 200)
            }
        ){
            Text("Charge Data")
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