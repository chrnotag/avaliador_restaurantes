package com.example.avaliador_restaurantes.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.avaliador_restaurantes.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val auth = FirebaseAuth.getInstance()//Instancia do Firebase Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Ação para logar o usuário
        binding.login.setOnClickListener {
            val Email = binding.emailLogin.text.toString()
            val Senha = binding.senhaLogin.text.toString()
            if (Email.isNotEmpty() && Senha.isNotEmpty()) {
                loginFirebase(Email, Senha)
            } else {
                Snackbar.make(
                    binding.root,
                    "Verifique os campos de login e senha e tente novamente",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        //Ação para abrir a tela de registro de usuário
        binding.register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    //Função para logar usuário
    fun loginFirebase(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Snackbar.make(
                        binding.root,
                        "Logado com Sucesso!",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                    startActivity(Intent(this, MainScreen::class.java))
                    this.finish()
                }
            }
            //Tratamento de exceções no login
            .addOnFailureListener {
                when (it) {
                    //Caso as credencias estejam erradas (usuario ou senha)
                    is FirebaseAuthInvalidCredentialsException -> {
                        Snackbar.make(
                            binding.root,
                            "Email ou senha incorretos",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    //Caso o usuário seja inválido
                    is FirebaseAuthInvalidUserException -> {
                        Snackbar.make(
                            binding.root,
                            "Email não registrado, por favor verifique!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    //Quando ocorre um erro não tratado
                    else -> {
                        Snackbar.make(
                            binding.root,
                            "Erro desconhecido",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }
}