package com.varqulabs.corrutinasdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FourthActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val exceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
            println("EXCEPCION FOR PARENT -> $throwable")
        }

        val scope = CoroutineScope(Job())

        scope.launch(exceptionHandler) {
            launch {
                println("Corrutina 1 Inicia...")
                delay(100)
                lanzarExcepcion()
                /*try {
                    lanzarExcepcion()
                }catch (e: Exception){
                    println("Exception Catched -> $e")
                }*/
            }

            launch {
                println("Corrutina 2 Inicia...")
                delay(2000)
                println("Corrutina 2 Terminada")
            }
        }


    }

}

private fun lanzarExcepcion(){
    throw NullPointerException()
}