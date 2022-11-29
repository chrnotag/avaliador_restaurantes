package com.example.avaliador_restaurantes.fragments.question1

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.avaliador_restaurantes.databinding.FragmentQuestion1Binding

class Question1 : Fragment() {

    companion object {
        fun newInstance() = Question1()
    }

    private lateinit var viewModel: Question1ViewModel

    private val binding by lazy {
        FragmentQuestion1Binding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Question1ViewModel::class.java)
        Sim() //Método para responder sim
        Nao() //Método para responder Não
    }

    //Método para pular para o proximo fragment enviando o valor 1 para sim
    private fun Sim() {
        binding.sim.setOnClickListener {
            nextFragment(1)
        }
    }

    //Método para pular para o proximo fragment enviando o valor 0 para não
    private fun Nao() {
        binding.nao.setOnClickListener {
            nextFragment(0)
        }
    }

    //método para chamar o proximo fragment enviando a resposta do usuário
    private fun nextFragment(value: Int) {
        val nav = Question1Directions.actionQuestion1ToQuestion2(value)
        binding.root.findNavController().navigate(nav)
    }

}