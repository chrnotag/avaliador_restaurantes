package com.example.avaliador_restaurantes.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.avaliador_restaurantes.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {


    /**
     * ESSA ACTIVITY É DESTINADA A FAZER A VERIFICAÇÃO DO LOGIN DO USUARIO NO APP
     * CASO O USUÁRIO ESTEJA LOGADO, SERÁ ENVIADO DIRETAMENTE PARA A MAINSCREEN
     * CASO O USUÁRIO NÃO ESTEJA LOGADO SERÁ ENVIADO PARA A TELA DE LOGIN
     */


    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Ocultar a Action Bar
        supportActionBar!!.hide()

        //Verificar Usuário Logado
        verificarLoginFirebase()
    }

    //Função para verificar se o dispositivo encontra-se logado no firebase
    //Caso esteja logado será levado para a tela inicial do App
    //caso contrario será levado a tela de login
    fun verificarLoginFirebase(){
        //Caso retorne nulo, não há usuário logado, caso contrario há usuario logado
        if(auth.currentUser != null){
            //Caso haja um usuário logado ele irá nos enviar para a tela inicial do app
            startActivity(Intent(this, MainScreen::class.java))
        }else {
            //Caso não haja um usuario logado ele irá enviar para a tela de login
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}