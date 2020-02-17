package br.com.caelum.casadocodigo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import br.com.caelum.casadocodigo.R
import br.com.caelum.casadocodigo.model.Autor
import br.com.caelum.casadocodigo.web.AutorWebClient
import kotlinx.android.synthetic.main.activity_autor.*

class AutorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autor)
    }

    private fun pegaDadosDoAutor(): Autor {
        val nome = nome_autor.text.toString()
        val linkGithub = linkGithub_autor.text.toString()

        val autor = Autor(nome, linkGithub)
        Log.i("AUTOR", "dados do autor: $nome, $linkGithub")

        return autor
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_autor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.salva_autor) {
            val autor = pegaDadosDoAutor()
            AutorWebClient().salva(autor)
        }
        return false
    }
}
