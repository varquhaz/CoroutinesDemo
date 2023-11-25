package com.varqulabs.corrutinasdemo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class MainState(
    val message: String = "",
    val label : String = ""
)

class MainViewModel(

): ViewModel(){

    var state by mutableStateOf(MainState())
        private set

    suspend fun llamarCorrutina(){
        //Corrutina que no retorna, solo hace (Procedimiento)
        viewModelScope.launch {
            llamarRoomLaunch()
        }

        //Corrutina que si retorna, como una funcion
        //podemos guardarlo en una funcion o en una variable
        val resultado = viewModelScope.async {
            llamarRoomAsync()
        }
        state = state.copy(
            message = resultado.await() // para usar el await()
            // al esperar un resultado, debemos de agregar suspend
        // a la funcion en la que esta dentro (llamarCorrutina())
        )
    }

    //viewModelScope.launch, lanzamos la corrutina (NO retorna resultado)
    // lo guardamos en nuestro estado : HomeState()
    // en este caso "message"                 // como un Procedimiento
    suspend fun llamarRoomLaunch(){
        delay(2000)
        state = state.copy(
            message = "Llamando a Room"
        )
    }

    //viewModelScope.async, lanzamos corrutina (SI retorna resultado)
    // deberemos de usar return o return@async    //como una Funcion
    suspend fun llamarRoomAsync(): String{
        delay(2000)
        return "Llamando a Room"
    }

    //funcion que hace lo mismo pero en una sola funcion, mas directo
    fun llamarRoomDirecto(){
        //Al Definir el viewModelScope.launch, no es necesario usar el suspend
        viewModelScope.launch {
            delay(2000)
        }
    }

    fun corrutinaQueEspera(){
        viewModelScope.launch(Dispatchers.IO) {
            val deferred = async {
                delay(2000)
                "Llamando a Retrofit"
                //Posteriormente poniendo el valor en deferred
            }
            //Si hacemos otra llamada a otra api
            val deferred2 = async {
                delay(2000)
                "Llamando a Room"
                //Posteriormente poniendo el valor en deferred2
            }

            val response = deferred.await() //No continua hasta obtener el resultado

            //No se ejecutara, esperara a deferred1
            val response2 = deferred2.await()

        }

    }

    fun corrutina1(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result2 = async { fetchDataAwait() }

                val respuesta = result2.await()
                fetchDataLaunch()
                state = state.copy(
                    message = "${respuesta} + ${state.message} "
                )
            } catch (e: Exception){
                state = state.copy(
                    message = "Error"
                )
            }
        }

    }

    private suspend fun fetchDataLaunch() {
        // Simular una operación asíncrona
        delay(2000)
        state = state.copy(
            message = "Datos obtenidos usando Launch"
        )
    }

    private suspend fun fetchDataAwait(): String {
        // Simular otra operación asíncrona
        delay(2000)
        return "Datos obtenidos usando Await"
    }





}