package br.com.caelum.casadocodigo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.caelum.casadocodigo.repository.AutorRepository
import br.com.caelum.casadocodigo.web.AutorWebClient

object ViewModelFactory : ViewModelProvider.Factory {
    private val autorWebClient = AutorWebClient()
    private val autorRepository = AutorRepository(autorWebClient)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = AutorViewModel(autorRepository) as T
}
