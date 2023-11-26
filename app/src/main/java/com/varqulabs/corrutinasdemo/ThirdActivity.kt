package com.varqulabs.corrutinasdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ThirdActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(2000)
            try {
                // Si dentro ponemos otra corrutina, launch{ throwException}
                // Crasheara, al estar anidada, se detendra
                throwException()
            }catch (e: Exception){
                //No crasheara por el try cacth
                println("Catched Exception -> $e")
            }
        }

    }

}

private fun throwException(){
    throw NullPointerException()
}