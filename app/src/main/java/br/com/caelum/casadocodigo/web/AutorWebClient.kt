package br.com.caelum.casadocodigo.web

import android.util.Log
import br.com.caelum.casadocodigo.model.Autor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class AutorWebClient {
    fun salva(autor: Autor) {
        val retrofit = getRetrofit()

        val autorService = retrofit.create(AutorService::class.java)
        autorService.salva(autor).enqueue(object : Callback<Unit?> {
            override fun onFailure(call: Call<Unit?>, t: Throwable) {
                Log.e("ERRO", "Não comunicou com o servidor", t)
            }

            override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
                if (response.isSuccessful) {
                    Log.i("SUCESSO", "autor ${autor.nome} salvo")
                } else {
                    Log.i("FALHA", "autor ${autor.nome} não rolou ${response?.raw()}")
                }
            }

        })
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://4191c1a9.ngrok.io")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

}

private interface AutorService {
    @POST("/api/autor")
    fun salva(@Body autor: Autor): Call<Unit?>
}
