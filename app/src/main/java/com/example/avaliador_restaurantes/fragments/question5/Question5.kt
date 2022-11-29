package com.example.avaliador_restaurantes.fragments.question5

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.avaliador_restaurantes.R
import com.example.avaliador_restaurantes.databinding.FragmentQuestion5Binding
import com.example.avaliador_restaurantes.fragments.question1.Question1Directions
import com.example.avaliador_restaurantes.fragments.question2.Question2Directions

class Question5 : Fragment() {

    companion object {
        fun newInstance() = Question5()
    }

    private lateinit var viewModel: Question5ViewModel

    private val binding by lazy {
        FragmentQuestion5Binding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Question5ViewModel::class.java)
        Sim()
        Nao()
        backFragment()
    }

    //Método para pular para o proximo fragment enviando o valor 1 para sim
    private fun Sim() {
        binding.q5sim.setOnClickListener {
            nextFragment(1)
        }
    }

    //Método para pular para o proximo fragment enviando o valor 0 para não
    private fun Nao() {
        binding.q5nao.setOnClickListener {
            nextFragment(0)
        }
    }

    //método para chamar o proximo fragment enviando a resposta do usuário
    private fun nextFragment(value: Int) {
        val nav = Question5Directions.actionQuestion5ToQuestion6(
            arguments?.getInt("q1")!!,
            arguments?.getInt("q2")!!,
            arguments?.getInt("q3")!!,
            arguments?.getInt("q4")!!,
            value
        )
        binding.root.findNavController().navigate(nav)
    }

    private fun backFragment() {
        binding.backq4.setOnClickListener {
            val nav = Question5Directions.actionQuestion5ToQuestion4(0, 0, 0)
            binding.root.findNavController().navigate(nav)
        }
    }
}