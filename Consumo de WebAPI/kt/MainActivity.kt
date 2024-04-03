package com.example.webapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.webapi.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    //Configura a URL base do retrofit, da api
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://catfact.ninja")
        .addConverterFactory(GsonConverterFactory.create())//Transforma o resultado em JSON
        .build()

    //Configura os métodos que devem ser usados pela api
    private val api = retrofit.create(CatFactAPI::class.java)//Pegamos a classe que criamos anteriormente e sao implementadas na API

    //Defino o binding
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Define a tela, passando a atividade e o layout
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        gerarNovaFrase()

        //Define evento de botáo
        binding.button.setOnClickListener {
            //Define a Thread que irá executar a funcao assincrona da API
            gerarNovaFrase()
        }
    }

    private fun gerarNovaFrase(){
        CoroutineScope(Dispatchers.Main).launch {
            //Defino o texto do textView como uma random joke da API
            val response = api.getRandomCatFact()
            binding.text.text = response.fact
        }
    }
}