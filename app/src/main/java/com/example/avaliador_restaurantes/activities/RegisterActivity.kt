package com.example.avaliador_restaurantes.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.avaliador_restaurantes.R
import com.example.avaliador_restaurantes.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthWebException

class RegisterActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Chamar a função para registrar usuários novos
        binding.registrar.setOnClickListener {
            val email = binding.emailRegistrar.text.toString()
            val senha = binding.senhaRegistrar.text.toString()
            val senhaRepetida = binding.senhaRegistrarRepetir.text.toString()
            //Verificando campos obrigatórios
            if (email.isNotEmpty()
                && senha.isNotEmpty()
                && senhaRepetida.isNotEmpty()
                && senha.equals(senhaRepetida)
            ) {
                RegisterUserFirebase(email, senha)
            } else {
                Snackbar.make(
                    binding.root,
                    "Verifique os campos, as senhas devem ser iguais!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    //Função para registrar usuários no firebase
    private fun RegisterUserFirebase(email: String, senha: String) {
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Snackbar.make(
                        binding.root,
                        "Registrado com sucesso!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, MainScreen::class.java))
                    this.finishAffinity()
                }
            }
            //Tratamento de exceções
            .addOnFailureListener {
                when (it) {
                    is FirebaseAuthEmailException -> {
                        Snackbar.make(
                            binding.root,
                            "Por favor insira um Email válido",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    is FirebaseAuthWeakPasswordException -> {
                        Snackbar.make(
                            binding.root,
                            "A senha deve ter no minimo 6 (seis) caracteres",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    is FirebaseAuthWebException -> {
                        Snackbar.make(
                            binding.root,
                            "Erro na rede, verifique a internet",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        Snackbar.make(
                            binding.root,
                            "Erro desconhecido, tente novamente mais tarde",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }
}