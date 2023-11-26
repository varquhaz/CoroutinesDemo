package com.varqulabs.corrutinasdemo

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.varqulabs.corrutinasdemo.data.local.FlowRepositoryImpl
import com.varqulabs.corrutinasdemo.ui.theme.CorrutinasDemoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

                   /* var lifecycle by remember {
                        mutableStateOf(Lifecycle.Event.ON_CREATE)
                    }
                    val lifecycleOwner = LocalLifecycleOwner.current

                    DisposableEffect(key1 = lifecycleOwner){
                        val observer = LifecycleEventObserver{ _, event ->
                            lifecycle = event
                        }
                        lifecycleOwner.lifecycle.addObserver(observer)
                        onDispose {
                            lifecycleOwner.lifecycle.removeObserver(observer)
                        }
                    }*/

                    /*val scope = rememberCoroutineScope()

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            val new = scope.launch {
                                println("Inicia la corrutina")
                                delay(5000)
                                println("Corrutina Finalizada")
                            }
                        }) {
                            Text("Iniciar Corrutina")
                        }

                        Button(onClick = {
                            scope.launch {  }
                            println("Corrutina cancelada")
                        }) {
                            Text("Cancelar corrutina")
                        }
                    }*/

                    /*val scope = rememberCoroutineScope()

                    var texto by remember{
                        mutableStateOf("Lanzar Suma")
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                CoroutineScope(Dispatchers.Main).async {
                                    val suma1 = async {
                                        delay(2000)
                                        suma(1,2)
                                    }
                                    val suma2 =
                                        suma(3, 3)


                                    println("La suma es ${suma1.await()+suma2}")
                                    val total = suma1.await() + suma2
                                    texto = "$total"
                                }
                            }
                        ){
                            Text(texto)
                        }
                    }*/

                    /*val scope = rememberCoroutineScope()

                    var text by remember {
                        mutableStateOf("")
                    }

                    LaunchedEffect(key1 = Unit){
                        withContext(Dispatchers.Main){
                            val result = fetchRemote()
                            text = result
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            lifecycleScope.launch {
                                while (true){
                                    delay(1000)
                                    println("Corriendo Corrutina")
                                }
                            }
                            GlobalScope.launch {
                                delay(5000)
                                Intent(applicationContext, SecondActivity::class.java).also {
                                    startActivity(it)
                                    finish()
                                }
                            }

                        }) {
                            Text("Click Me")
                        }
                        Text(text)
                    }*/

                    Button(onClick = {
                        Intent(applicationContext, FourthActivity::class.java).also {
                            startActivity(it)
                            finish()
                        }
                    }) {
                        Text("Click Me to navigate 4th Activity")
                    }



                }
            }
        }
    }
}

suspend fun fetchRemote(): String{
    delay(6000)
    return "Succesfull Data from Remote"
}

suspend fun suma(num1: Int, num2: Int): Int {
    return num1 + num2
}

//funcion Yield()
// para saltar de la corrutina en la cual se invoco yield()
// a otra corrutina si es que la hay

//LaunchedEffect() : Codigo, corrutina que se ejecuta cuando inicia el Composable
// DiposableEffect() : igual que el LaunchedEffect, pero cuando el Composable ya no se ve
// Tiene un atributo/parametro onDispose{}, que ejecutara un codigo/corrutina cuando ya no se vea el composable

// Con var lifecycleOwner = LocalLifeCycleOwner.current obtenemos el ciclo de vida actual de la app
// y con lifecycleOwner.observe() le pasamos Lifecycle.Event. -> ON_CREATE, ON_START, ON_RESUME, ON_PAUSE, ON_STOP, ON_DESTROY
// y ejecutar una accion cuando ocurra ese Evento del ciclo de vida.{}

@Composable
fun LongTaskComp() {
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