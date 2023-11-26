package com.varqulabs.corrutinasdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.varqulabs.corrutinasdemo.ui.theme.CorrutinasDemoTheme
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class SecondActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val exceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
            println("Exception Escuchada")
        }

        val coroutineScope = CoroutineScope(Dispatchers.Unconfined + exceptionHandler)

        val job1 = coroutineScope.launch {
            println("JOb1 started")
            delay(100)
            throw Exception()
        }



        val job2 = coroutineScope.launch {
            println("Job2 Started")
            delay(200)
            println("Job2 Finalized")
        }

        /*// Con withTimeOut establecemos un tiempo para que se complete la corrutina en caso de no terminarse, se cancelara
        val job1 = coroutineScope.launch {
            withTimeout(500){
                delay(1000)
            }
        }

        val job2 = coroutineScope.launch {
            delay(1000)
        }*/

        Thread.sleep(2000)

        println("Job1 -> ${job1.status()}")
        println("Job2 -> ${job2.status()}")
        println("Job padre continua activo: -> ${coroutineScope.isActive}")

        setContent{
            CorrutinasDemoTheme {
                Text("Second ACtivity")
            }
        }
    }

}

fun Job.status(): String = when {
    isCancelled -> "Cancelado"
    isActive -> "Activo"
    isCompleted -> "Completado"
    else -> "Nothing"
}