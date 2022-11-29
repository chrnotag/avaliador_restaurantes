package com.example.avaliador_restaurantes.fragments.question4

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.avaliador_restaurantes.R
import com.example.avaliador_restaurantes.databinding.FragmentQuestion4Binding
import com.example.avaliador_restaurantes.fragments.question1.Question1Directions
import com.example.avaliador_restaurantes.fragments.question2.Question2Directions

class Question4 : Fragment() {

    companion object {
        fun newInstance() = Question4()
    }

    private lateinit var viewModel: Question4ViewModel

    private val binding by lazy {
        FragmentQuestion4Binding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Question4ViewModel::class.java)
        Sim()
        Nao()
        backFragment()
    }

    //Método para pular para o proximo fragment enviando o valor 1 para sim
    private fun Sim() {
        binding.q4sim.setOnClickListener {
            nextFragment(1)
        }
    }

    //Método para pular para o proximo fragment enviando o valor 0 para não
    private fun Nao() {
        binding.q4nao.setOnClickListener {
            nextFragment(0)
        }
    }

    //método para chamar o proximo fragment enviando a resposta do usuário
    private fun nextFragment(value: Int) {
        val nav = Question4Directions.actionQuestion4ToQuestion5(
            arguments?.getInt("q1")!!,
            arguments?.getInt("q2")!!,
            arguments?.getInt("q3")!!,
            value
        )
        binding.root.findNavController().navigate(nav)
    }

    private fun backFragment() {
        binding.backq3.setOnClickListener {
            val nav = Question4Directions.actionQuestion4ToQuestion3(0, 0)
            binding.root.findNavController().navigate(nav)
        }
    }

}