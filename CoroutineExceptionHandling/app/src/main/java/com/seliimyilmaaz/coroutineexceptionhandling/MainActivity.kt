package com.seliimyilmaaz.coroutineexceptionhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var handler = CoroutineExceptionHandler{coroutineContext , throwable ->
            println("Exception : " + throwable.message)
        }

      /*  lifecycleScope.launch(handler){
            launch {
                throw Exception("Error catch")

                launch {
                    delay(500)
                    println("The progress continue.")       //This code not reachable because when any of coroutines launch catch the error under the code won't work
                }
            }
        }*/


            lifecycleScope.launch(handler){
                supervisorScope {
                    launch {
                        throw Exception("Error catch")
                    }

                    launch {
                        delay(500)
                        println("The progress continue.")
                    }
                }
            }

        CoroutineScope(Dispatchers.Main + handler).launch{
            throw Exception("Exception catch in coroutine scope")
        }

    }
}