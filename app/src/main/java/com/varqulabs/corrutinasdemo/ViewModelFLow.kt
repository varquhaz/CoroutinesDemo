package com.varqulabs.corrutinasdemo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varqulabs.corrutinasdemo.data.local.FlowRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ViewModelFLow(
    private val repositoryImpl: FlowRepositoryImpl
): ViewModel() {

    var state by mutableStateOf("Begin")
        private set

    fun getDataRepository(){
        viewModelScope.launch {
            repositoryImpl.getDataNumbers().collect{
                state = it
            }
        }
    }



    /*fun beginTimer(){
        viewModelScope.launch {
            for(i in 0..5){
                if(state == "Begin"){
                    state = "$i"
                } else {
                    delay(1000)
                    state = "$i"
                }
            }
        }
    }*/



    fun getData(){
        viewModelScope.launch {
            var result = returnDataFlow().collectLatest { numero ->
                state = numero
            }
        }
    }

    fun returnDataFlow(): Flow<String> {
        return flow {
            for(i in 0..5){
                if(state == "Begin"){
                    emit("$i")
                } else {
                    delay(1000)
                    emit("$i")
                }
            }
        }
    }


    //clase repository
    // llamar al metodo de Flow y al begin

}

