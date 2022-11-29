package com.example.avaliador_restaurantes.fragments.question6

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.room.Room
import com.example.avaliador_restaurantes.BEAN.RestaurantBean
import com.example.avaliador_restaurantes.BEAN.mediaGeralBEAN
import com.example.avaliador_restaurantes.DAO.mediaGeralDAO
import com.example.avaliador_restaurantes.DAO.restaurantDAO
import com.example.avaliador_restaurantes.DBAcess.DBAcess
import com.example.avaliador_restaurantes.R
import com.example.avaliador_restaurantes.activities.MainScreen
import com.example.avaliador_restaurantes.activities.OpenRestaurantActivity
import com.example.avaliador_restaurantes.databinding.FragmentQuestion6Binding
import com.example.avaliador_restaurantes.fragments.question1.Question1Directions
import com.example.avaliador_restaurantes.fragments.question2.Question2Directions
import com.example.avaliador_restaurantes.utils.formatDecimal
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.roundToInt

class Question6 : Fragment() {

    companion object {
        fun newInstance() = Question6()
    }

    private lateinit var viewModel: Question6ViewModel

    private val binding by lazy {
        FragmentQuestion6Binding.inflate(layoutInflater)
    }

    private var value = 0
    private var IdTable: Long = 0
    private val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Question6ViewModel::class.java)

        //Instanciação do BD
        val bd = DBAcess.getDatabase(requireContext())

        //Resgatando o valor do ID da tabela em uma variavel global
        IdTable = activity?.intent!!.getLongExtra("id", 0)

        //Configuração do BD com os modelos DAO
        val restaurantDao = bd.restauranteDAO()
        val mediaGeralDAO = bd.mediaGeralDAO()

        //Ação do clique na opção Sim
        binding.q6sim.setOnClickListener {
            value = 1
            mostrarBotaoCompletar()
        }

        //Ação do clique na opção Não
        binding.q6nao.setOnClickListener {
            value = 0
            mostrarBotaoCompletar()
        }

        //Ação do clique no botão Confirmar
        binding.avaliationComplete.setOnClickListener {
            salvarDados(restaurantDao, mediaGeralDAO)
            startActivity(Intent(requireContext(), MainScreen::class.java))
            this.activity?.finishAffinity()
        }

        backFragment()

    }

    //Método usado para retornar ao fragmento anterior
    private fun backFragment() {
        binding.backq5.setOnClickListener {
            val nav = Question6Directions.actionQuestion6ToQuestion5(0, 0, 0, 0)
            binding.root.findNavController().navigate(nav)
        }
    }

    //Função usada para deixar o botão Complete visivel quando uma resposta para a pergunta for escolhida
    private fun mostrarBotaoCompletar() {
        binding.avaliationComplete.visibility = View.VISIBLE
    }

    //Função usada para salvar as respostas e a média total no BD
    private fun salvarDados(restaurantDao: restaurantDAO, mediaGeralDAO: mediaGeralDAO) {
        val total = arguments?.getInt("q1")!! +
                arguments?.getInt("q2")!! +
                arguments?.getInt("q3")!! +
                arguments?.getInt("q4")!! +
                arguments?.getInt("q5")!! +
                value

        //Configuração do calculo de média total
        var mediaTotal = 0.0
        var count = 0
        //Calculo de média total
        val mediaNota: Double = (total.toDouble() / 6) * 100
        //inserindo as respostas junto com a média total na classe BEAN
        val salvarMedia = mediaGeralBEAN(
            restaurant_id = IdTable, uidUser = uid, media = formatDecimal(mediaNota).format,
            Q1 = arguments?.getInt("q1")!!,
            Q2 = arguments?.getInt("q2")!!,
            Q3 = arguments?.getInt("q3")!!,
            Q4 = arguments?.getInt("q4")!!,
            Q5 = arguments?.getInt("q5")!!,
            Q6 = value
        )
        //Método para salvar os dados da classe BEAN
        mediaGeralDAO.insertAll(salvarMedia)

        //método para somar todas as médias referentes a esse restaurante
        //há também um contador para saber quantas avaliações ele teve
        val dados = mediaGeralDAO.getAllMedia()
        dados.forEach() { doc ->
            mediaTotal += doc.media
            count++
        }

        //Calculo da média geral utilizando todas as médias de avaliação de todos os usuarios
        //que avaliaram esse restaurante
        val mediaGeral: Double = (mediaTotal / count)

        //Salvando a media geral no BD
        restaurantDao.updateMedia(
            media = formatDecimal(mediaGeral).format,
            id = IdTable
        )
    }
}