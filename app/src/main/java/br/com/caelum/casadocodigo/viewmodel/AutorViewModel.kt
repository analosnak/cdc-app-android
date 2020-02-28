package br.com.caelum.casadocodigo.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.caelum.casadocodigo.model.Autor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class AutorViewModel : ViewModel() {
    private val nomeValido = MutableLiveData<String>()
    private val githubLinkValido = MutableLiveData<String>()
    val autorValido = MutableLiveData<Autor>()
    val erroNome = MutableLiveData<String>()
    val erroGithubLink = MutableLiveData<String>()

    fun validaForm(nomeAutor: String, linkgithubAutor: String) {
        val nomeEhValido = isNomeValido(nomeAutor)
        val urlEhValida = isUrlValida(linkgithubAutor)

        if (nomeEhValido && urlEhValida) {
            autorValido.postValue(Autor(nomeAutor, linkgithubAutor))
        }
    }

    private fun isNomeValido(nome: String): Boolean {
        if (nome.isBlank()) {
            erroNome.postValue("O nome é obrigatório")
            return false
        } else {
            nomeValido.postValue(nome)
            return true
        }
    }

    private fun isUrlValida(githubLink: String): Boolean {
        if (githubLink.isBlank()) {
            erroGithubLink.postValue("A URL é obrigatória")
            return false
        } else if (!Patterns.WEB_URL.matcher(githubLink).matches()) {
            erroGithubLink.postValue("URL inválida")
            return false
        } else {
            githubLinkValido.postValue(githubLink)
            return true
        }
    }

    fun salva(autor: Autor) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://8b27b7a2.ngrok.io")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        val autorService = retrofit.create(AutorService::class.java)

        autorService.salva(autor).enqueue(object : Callback<Unit?> {
            override fun onFailure(call: Call<Unit?>, t: Throwable) {
                Log.e("ERRO", "Não comunicou com o servidor", t)
            }

            override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
                if (response.isSuccessful) {
                    Log.i("SUCESSO", "autor ${autor.nome} salvo")
                } else {
                    Log.i("FALHA", "autor ${autor.nome} não rolou ${response.raw()}")
                }
            }

        })
    }

    private interface AutorService {
        @POST("/api/autor")
        fun salva(@Body autor: Autor): Call<Unit?>
    }

}