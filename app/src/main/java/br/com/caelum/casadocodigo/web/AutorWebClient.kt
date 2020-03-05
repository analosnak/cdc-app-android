package br.com.caelum.casadocodigo.web

import br.com.caelum.casadocodigo.model.Autor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

class AutorWebClient(private val autorService: AutorService) {

    fun salva(
        autor: Autor,
        sucesso: (Autor) -> Unit,
        erroServidor: (Throwable) -> Unit,
        falha: (Autor, String) -> Unit
    ) {

        autorService.salva(autor).enqueue(object : Callback<Unit?> {
            override fun onFailure(call: Call<Unit?>, t: Throwable) {
                erroServidor(t)
            }

            override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
                if (response.isSuccessful) {
                    sucesso(autor)
                } else {
                    falha(autor, response.message())
                }
            }

        })
    }

}

interface AutorService {
    @POST("/api/autor")
    fun salva(@Body autor: Autor): Call<Unit?>
}
