package com.example.avaliador_restaurantes.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import com.example.avaliador_restaurantes.R
import com.example.avaliador_restaurantes.databinding.ActivityAvaliarRestauranteBinding

class AvaliarRestaurante : AppCompatActivity() {

    private val binding by lazy {
        ActivityAvaliarRestauranteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Configurando a toolbar
        val toolbar = supportActionBar!!
        toolbar.title = ""
        toolbar.setHomeButtonEnabled(true)
        toolbar.setDisplayHomeAsUpEnabled(true)

        //Configurando os navhost
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
    }

    //Configurando a ação do click no botão da toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

}