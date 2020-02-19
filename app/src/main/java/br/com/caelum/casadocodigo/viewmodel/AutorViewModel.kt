package br.com.caelum.casadocodigo.viewmodel

import android.util.Patterns
import android.widget.EditText
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.caelum.casadocodigo.model.Autor
import br.com.caelum.casadocodigo.repository.AutorRepository
import br.com.caelum.casadocodigo.web.AutorWebClient

class AutorViewModel private constructor(private val autorRepository: AutorRepository) : ViewModel() {
    private val nomeValido = MutableLiveData<Boolean>()
    private val githubLinkValido = MutableLiveData<Boolean>()
    val erroNome = MutableLiveData<String>()
    val erroGithubLink = MutableLiveData<String>()

    val formValido = MediatorLiveData<Boolean>().apply {
        addSource(githubLinkValido) { urlEhValida ->
            value = nomeValido.value?.and(urlEhValida)
        }
        addSource(nomeValido) { nomeEhValido ->
            value = githubLinkValido.value?.and(nomeEhValido)
        }
    }

    fun validaForm(nomeAutor: String, linkgithubAutor: String) {
        validaNome(nomeAutor)
        validaUrl(linkgithubAutor)
    }

    private fun validaNome(nome: String) {
        if (nome.isBlank()) {
            nomeValido.postValue(false)
            erroNome.postValue("O nome é obrigatório")
        }
        nomeValido.postValue(true)
    }

    private fun validaUrl(githubLink: String) {
        if (!Patterns.WEB_URL.matcher(githubLink).matches()) {
            githubLinkValido.postValue(false)
            erroGithubLink.postValue("URL inválida")
        }
        if (githubLink.isBlank()) {
            githubLinkValido.postValue(false)
            erroGithubLink.postValue("A URL é obrigatória")
        }
        githubLinkValido.postValue(true)
    }

    fun salva(autor: Autor) = autorRepository.salva(autor)

    object Factory : ViewModelProvider.Factory {
        private val autorWebClient = AutorWebClient()
        private val autorRepository = AutorRepository(autorWebClient)

        override fun <T : ViewModel?> create(modelClass: Class<T>): T = AutorViewModel(autorRepository) as T
    }
}