package com.angel.coroutineszerotohero

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.angel.coroutineszerotohero.ui.theme.CoroutinesZeroToHeroTheme
import com.angel.coroutineszerotohero.ui.theme.RetrofitHelper
import com.angel.coroutineszerotohero.ui.theme.SuperHeroDataResponse
import retrofit2.Response


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var retrofit = RetrofitHelper.getInstance()
        var resultado: Response<SuperHeroDataResponse>? = null
        lifecycleScope.launch(Dispatchers.IO){
            resultado = retrofit.getSuperheroes("a")
            withContext(Dispatchers.Main){
                if(resultado!!.isSuccessful){
                    Log.i("lista", "$resultado")
                    Log.i("lista", "${resultado!!.body().toString()}")
                }
            }
        }
        setContent {
            CoroutinesZeroToHeroTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SuperHeroList(
                        modifier = Modifier.padding(innerPadding),
                        resultado
                    )
                }
            }
        }
    }
}

@Composable
fun SuperHeroList(modifier: Modifier = Modifier, resultado: Response<SuperHeroDataResponse>?) {
    if (resultado?.isSuccessful == true) {
        resultado.body()?.let { superHeroe ->
            superHeroe.results.forEach { hero ->
                Text(text = hero.name)
            }
        }?: run {
            println("No tengo nombre")
        }
    } else{
        Text(text = "Error al cargar los datos")
    }
}