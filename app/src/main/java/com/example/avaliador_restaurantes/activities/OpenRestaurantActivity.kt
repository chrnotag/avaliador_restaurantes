package com.example.avaliador_restaurantes.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.avaliador_restaurantes.BEAN.mediaGeralBEAN
import com.example.avaliador_restaurantes.DAO.mediaGeralDAO
import com.example.avaliador_restaurantes.DBAcess.DBAcess
import com.example.avaliador_restaurantes.R
import com.example.avaliador_restaurantes.databinding.ActivityOpenRestaurantBinding
import com.example.avaliador_restaurantes.utils.byteToDrawable
import com.google.firebase.auth.FirebaseAuth

class OpenRestaurantActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityOpenRestaurantBinding.inflate(layoutInflater)
    }

    var ID: Long = 0    //Para guardar o ID vindo da activite anterior
    val auth = FirebaseAuth.getInstance()   //Acesso ao firebaseAuth
    var AVALIAR = true  //Controlador para saber se o usuário já avaliou ou não esse restaurante

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Configurações da toolbar
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        //Resgate do valor vindo da outra activity
        ID = intent.getLongExtra("id", 0)

        //Instancia do modelo DAO pelo banco de dados
        val db = DBAcess.getDatabase(this)

        checarAvaliacaoUsuario(ID, db.mediaGeralDAO())  //Verificar se o usuário já avaliou
        preencherCampos(db.mediaGeralDAO()) //Preencher os elementos da UI com os dados do BD
        ajustarEstrelas()   //Ajustar as estrelas da UI baseadas na média total resgatada do BD

        //Ação do botão avaliar
        binding.avaliarButton.setOnClickListener {
            //Caso o usuário não tenha avaliado
            if (AVALIAR) {
                val intent = Intent(this, AvaliarRestaurante::class.java)
                intent.putExtra("id", ID)
                startActivity(intent)

                //Caso já tenha avaliado
            } else {
                val intent = Intent(this, verificarAvaliacao::class.java)
                intent.putExtra("id", ID)
                intent.putExtra("uidUser", auth.currentUser!!.uid)
                startActivity(intent)
            }
        }
    }

    //Configura os campos da UI com os dados retornados do BD
    private fun preencherCampos(dao: mediaGeralDAO) {
        binding.imgRestaurante.setImageDrawable(
            intent.getByteArrayExtra("imgRestaurante")?.let {
                byteToDrawable(
                    it
                ).drawable
            })
        binding.numeroAvaliacoes.text =
            "Basedo em avaliações de ${QuantidadeAvaliacoes(ID, dao)} Usuarios"
        binding.bairro.text = intent.getStringExtra("bairro")
        binding.cidade.text = intent.getStringExtra("cidade")
        binding.logradouro.text = intent.getStringExtra("logradouro")
        binding.numero.text = intent.getStringExtra("numero")
        binding.notasGerente.text = intent.getStringExtra("notasGerente")
        binding.notaTotalRestaurante.text =
            "Nota total: ${intent.getDoubleExtra("notaTotal", 0.0)}"
        binding.nomeRestaurante.text = intent.getStringExtra("nomeRestaurante")
    }

    //Ajusta as estrelas com a media retornada do BD
    private fun ajustarEstrelas() {
        val media = intent.getDoubleExtra("notaTotal", 0.0)
        if (media != null) {
            if (media >= 0 && media <= 5) {
                binding.st1.setImageResource(R.drawable.star_border)
                binding.st2.setImageResource(R.drawable.star_border)
                binding.st3.setImageResource(R.drawable.star_border)
                binding.st4.setImageResource(R.drawable.star_border)
                binding.st5.setImageResource(R.drawable.star_border)
            } else if (media!! > 6 && media < 10) {
                binding.st1.setImageResource(R.drawable.half_star)
                binding.st2.setImageResource(R.drawable.star_border)
                binding.st3.setImageResource(R.drawable.star_border)
                binding.st4.setImageResource(R.drawable.star_border)
                binding.st5.setImageResource(R.drawable.star_border)
            } else if (media > 11 && media < 20) {
                binding.st1.setImageResource(R.drawable.star_full)
                binding.st2.setImageResource(R.drawable.star_border)
                binding.st3.setImageResource(R.drawable.star_border)
                binding.st4.setImageResource(R.drawable.star_border)
                binding.st5.setImageResource(R.drawable.star_border)
            } else if (media > 21 && media < 30) {
                binding.st1.setImageResource(R.drawable.star_full)
                binding.st2.setImageResource(R.drawable.half_star)
                binding.st3.setImageResource(R.drawable.star_border)
                binding.st4.setImageResource(R.drawable.star_border)
                binding.st5.setImageResource(R.drawable.star_border)
            } else if (media > 31 && media < 40) {
                binding.st1.setImageResource(R.drawable.star_full)
                binding.st2.setImageResource(R.drawable.star_full)
                binding.st3.setImageResource(R.drawable.star_border)
                binding.st4.setImageResource(R.drawable.star_border)
                binding.st5.setImageResource(R.drawable.star_border)
            } else if (media > 41 && media < 50) {
                binding.st1.setImageResource(R.drawable.star_full)
                binding.st2.setImageResource(R.drawable.star_full)
                binding.st3.setImageResource(R.drawable.half_star)
                binding.st4.setImageResource(R.drawable.star_border)
                binding.st5.setImageResource(R.drawable.star_border)
            } else if (media > 51 && media < 60) {
                binding.st1.setImageResource(R.drawable.star_full)
                binding.st2.setImageResource(R.drawable.star_full)
                binding.st3.setImageResource(R.drawable.star_full)
                binding.st4.setImageResource(R.drawable.star_border)
                binding.st5.setImageResource(R.drawable.star_border)
            } else if (media > 61 && media < 70) {
                binding.st1.setImageResource(R.drawable.star_full)
                binding.st2.setImageResource(R.drawable.star_full)
                binding.st3.setImageResource(R.drawable.star_full)
                binding.st4.setImageResource(R.drawable.half_star)
                binding.st5.setImageResource(R.drawable.star_border)
            } else if (media > 71 && media < 80) {
                binding.st1.setImageResource(R.drawable.star_full)
                binding.st2.setImageResource(R.drawable.star_full)
                binding.st3.setImageResource(R.drawable.star_full)
                binding.st4.setImageResource(R.drawable.star_full)
                binding.st5.setImageResource(R.drawable.star_border)
            } else if (media > 81 && media < 90) {
                binding.st1.setImageResource(R.drawable.star_full)
                binding.st2.setImageResource(R.drawable.star_full)
                binding.st3.setImageResource(R.drawable.star_full)
                binding.st4.setImageResource(R.drawable.star_full)
                binding.st5.setImageResource(R.drawable.half_star)
            } else if (media >= 91) {
                binding.st1.setImageResource(R.drawable.star_full)
                binding.st2.setImageResource(R.drawable.star_full)
                binding.st3.setImageResource(R.drawable.star_full)
                binding.st4.setImageResource(R.drawable.star_full)
                binding.st5.setImageResource(R.drawable.star_full)
            }
        }
    }

    //Checa se o usuário já avaliou o restaurante
    private fun checarAvaliacaoUsuario(id: Long, dao: mediaGeralDAO) {
        val dados = dao.getAvaliationUser(id)
        dados.forEach {
            if (it == auth.currentUser!!.uid) {
                binding.avaliarButton.text = "Verificar Avaliacao"
                AVALIAR = false
            }
        }
    }

    //Retonar a quantidade de usuários unicos que já avaliaram esse restaurante
    private fun QuantidadeAvaliacoes(id: Long, dao: mediaGeralDAO): Int {
        val dados = dao.getAvaliationUser(id)
        return dados.size
    }

    //Ação do botão da toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                returnHome()
            }
        }
        return true
    }

    //Ação do botão voltar nativo
    override fun onBackPressed() {
        super.onBackPressed()
        returnHome()
    }

    //Método para limpar a pilha de activities e voltar para a tela principal
    private fun returnHome() {
        startActivity(Intent(this, MainScreen::class.java))
        finishAffinity()
    }

}