package com.example.webapi

import retrofit2.http.GET

interface CatFactAPI {
    @GET("fact")
    suspend fun getRandomCatFact() : CatFact //Funcao assíncrona
}