package br.com.caelum.casadocodigo.web

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object InicializadorDeRetrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://7e6b29d5.ngrok.io")
        .addConverterFactory(JacksonConverterFactory.create())
        .build()
}