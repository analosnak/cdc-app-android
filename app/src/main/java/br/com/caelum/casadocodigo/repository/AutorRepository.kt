package br.com.caelum.casadocodigo.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.caelum.casadocodigo.model.Autor
import br.com.caelum.casadocodigo.web.AutorWebClient
import retrofit2.Response

class AutorRepository(private val client: AutorWebClient) {
    fun salva(autor: Autor) = client.salva(autor, sucesso, erroServidor, falha)

    val mensagemProToast = MutableLiveData<String>()

    val sucesso: (Autor) -> Unit = { autor: Autor ->
        Log.i("SUCESSO", "autor ${autor.nome} salvo")
        mensagemProToast.value = "autor ${autor.nome} salvo"
    }

    val erroServidor: (Throwable) -> Unit = {
        Log.e("ERRO", "Não comunicou com o servidor", it)
        mensagemProToast.value = "Não comunicou com o servidor"
    }

    val falha: (Autor, String) -> Unit = { autor: Autor, mensagem: String ->
        Log.i("FALHA", "autor ${autor.nome} não rolou $mensagem")
        mensagemProToast.value = "autor ${autor.nome} não rolou salvar"
    }

}