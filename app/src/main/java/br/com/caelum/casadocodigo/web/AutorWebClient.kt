package br.com.caelum.casadocodigo.web

import android.util.Log
import br.com.caelum.casadocodigo.model.Autor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

class AutorWebClient(retrofit: Retrofit) {
    private val autorService = retrofit.create(AutorService::class.java)

    fun salva(
        autor: Autor,
        sucesso: (Autor) -> Unit,
        erroServidor: (Throwable) -> Unit,
        falha: (Autor, Response<Unit?>) -> Unit
    ) {

        autorService.salva(autor).enqueue(object : Callback<Unit?> {
            override fun onFailure(call: Call<Unit?>, t: Throwable) {
                erroServidor(t)
            }

            override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
                if (response.isSuccessful) {
                    sucesso(autor)
                } else {
                    falha(autor, response)
                }
            }

        })
    }

}

private interface AutorService {
    @POST("/api/autor")
    fun salva(@Body autor: Autor): Call<Unit?>
}
