package com.example.avaliador_restaurantes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.avaliador_restaurantes.DBAcess.DBAcess
import com.example.avaliador_restaurantes.R
import com.example.avaliador_restaurantes.databinding.ActivityVerificarAvaliacaoBinding

class verificarAvaliacao : AppCompatActivity() {

    private val binding by lazy {
        ActivityVerificarAvaliacaoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Configuração do BD
        val db = DBAcess.getDatabase(this)
        val dao = db.mediaGeralDAO()

        //Configuração da toolbar
        val toolbar = supportActionBar!!
        toolbar.setHomeButtonEnabled(true)
        toolbar.setDisplayHomeAsUpEnabled(true)
        toolbar.title = ""

        //Resgatando os resultados do banco de dados
        val resultados =
            intent.getStringExtra("uidUser")
                ?.let { dao.getAvaliacao(intent.getLongExtra("id", 0), it) }
        //Configurando a UI com os dados resgatados do BD
        for (doc in resultados!!) {
            binding.RQ1.text = if (doc.Q6 == 0) "Sim" else "Não"
            binding.RQ2.text = if (doc.Q5 == 0) "Sim" else "Não"
            binding.RQ3.text = if (doc.Q4 == 0) "Sim" else "Não"
            binding.RQ4.text = if (doc.Q3 == 0) "Sim" else "Não"
            binding.RQ5.text = if (doc.Q2 == 0) "Sim" else "Não"
            binding.RQ6.text = if (doc.Q1 == 0) "Sim" else "Não"
        }
    }

    //Configurando ação do botão da toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }
}