package br.com.caelum.casadocodigo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.caelum.casadocodigo.R
import br.com.caelum.casadocodigo.model.Autor
import br.com.caelum.casadocodigo.viewmodel.AutorViewModel
import kotlinx.android.synthetic.main.activity_autor.*

class AutorActivity : AppCompatActivity() {
    
    private val autorViewModel: AutorViewModel by lazy {
        ViewModelProvider(this).get(AutorViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autor)

        autorViewModel.erroNome.observe(this, Observer { nome_autor.error = it })
        autorViewModel.erroGithubLink.observe(this, Observer { linkGithub_autor.error = it })

        autorViewModel.autorValido.observe(this, Observer { autorViewModel.salva(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_autor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.salva_autor) {
            autorViewModel.validaForm(nome_autor.text.toString(), linkGithub_autor.text.toString())
        }
        return false
    }
}
