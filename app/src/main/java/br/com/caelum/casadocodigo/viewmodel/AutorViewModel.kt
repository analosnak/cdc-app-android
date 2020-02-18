package br.com.caelum.casadocodigo.viewmodel

import androidx.lifecycle.ViewModel
import br.com.caelum.casadocodigo.model.Autor
import br.com.caelum.casadocodigo.repository.AutorRepository

class AutorViewModel(private val autorRepository: AutorRepository) : ViewModel() {
    fun salva(autor: Autor) = autorRepository.salva(autor)
}