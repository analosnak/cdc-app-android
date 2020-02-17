package br.com.caelum.casadocodigo.repository

import br.com.caelum.casadocodigo.model.Autor
import br.com.caelum.casadocodigo.web.AutorWebClient

class AutorRepository(private val client: AutorWebClient) {
    fun salva(autor: Autor) {
        client.salva(autor)
    }
}