package com.varqulabs.corrutinasdemo.data.local

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface FlowRepository {
    fun getDataNumbers(): Flow<String>
}
class FlowRepositoryImpl(

): FlowRepository {
    override fun getDataNumbers(): Flow<String> {
        val numbers = 0..5
        return flow {
            numbers.forEach {
                if(it == 0){
                    emit("$it")
                } else {
                    delay(1000)
                    emit("$it")
                }
            }
        }
    }
}
