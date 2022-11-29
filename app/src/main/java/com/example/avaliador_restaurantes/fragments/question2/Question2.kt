package com.example.avaliador_restaurantes.fragments.question2

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.avaliador_restaurantes.R
import com.example.avaliador_restaurantes.databinding.FragmentQuestion2Binding
import com.example.avaliador_restaurantes.fragments.question1.Question1Directions

class Question2 : Fragment() {

    companion object {
        fun newInstance() = Question2()
    }

    private lateinit var viewModel: Question2ViewModel
private val binding by lazy {
    FragmentQuestion2Binding.inflate(layoutInflater)
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Question2ViewModel::class.java)

        Sim()
        Nao()
        backFragment()
    }

    //Método para pular para o proximo fragment enviando o valor 1 para sim
    private fun Sim() {
        binding.q2sim.setOnClickListener {
            nextFragment(1)
        }
    }

    //Método para pular para o proximo fragment enviando o valor 0 para não
    private fun Nao() {
        binding.q2nao.setOnClickListener {
            nextFragment(0)
        }
    }

    //método para chamar o proximo fragment enviando a resposta do usuário
    private fun nextFragment(value: Int) {
        val nav = Question2Directions.actionQuestion2ToQuestion3(arguments?.getInt("q1")!!,value)
        binding.root.findNavController().navigate(nav)
    }

    private fun backFragment(){
       binding.backQ1.setOnClickListener {
           val nav = Question2Directions.actionQuestion2ToQuestion1(0)
           binding.root.findNavController().navigate(nav)
       }
    }

}