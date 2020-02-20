package br.com.caelum.casadocodigo.viewmodel

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.caelum.casadocodigo.model.Autor
import br.com.caelum.casadocodigo.repository.AutorRepository
import br.com.caelum.casadocodigo.web.AutorWebClient

class AutorViewModel private constructor(private val autorRepository: AutorRepository) : ViewModel() {
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

    fun salva(autor: Autor) = autorRepository.salva(autor)

    object Factory : ViewModelProvider.Factory {
        private val autorWebClient = AutorWebClient()
        private val autorRepository = AutorRepository(autorWebClient)

        override fun <T : ViewModel?> create(modelClass: Class<T>): T = AutorViewModel(autorRepository) as T
    }
}