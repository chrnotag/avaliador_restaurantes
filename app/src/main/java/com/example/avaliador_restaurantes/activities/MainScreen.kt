package com.example.avaliador_restaurantes.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.avaliador_restaurantes.Adapter.RecyclerRestaurantAdapter
import com.example.avaliador_restaurantes.BEAN.RestaurantBean
import com.example.avaliador_restaurantes.DAO.restaurantDAO
import com.example.avaliador_restaurantes.DBAcess.DBAcess
import com.example.avaliador_restaurantes.R
import com.example.avaliador_restaurantes.databinding.ActivityMainScreenBinding
import com.google.firebase.auth.FirebaseAuth

class MainScreen : AppCompatActivity() {

    //Vinculo de visualização
    private val binding by lazy {
        ActivityMainScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) //Utilizando o vinculo de visualização

        //Iniciando o banco de dados
        val db = DBAcess.getDatabase(this)
        val dao = db.restauranteDAO()

        //Personalização da Toolbar
        val toolbar = supportActionBar!!
        toolbar.title = "Avaliador de Restaurantes"

        val adapter = RecyclerRestaurantAdapter(loadAll(dao)) //Adaptador do RecyclerView
        val layoutManager = GridLayoutManager(this, 2) //Layout Manager utilizando o grid Layout

        binding.recyclerRestaurants.adapter = adapter //Inserindo o adaptador no recyclerview
        binding.recyclerRestaurants.layoutManager = layoutManager //Utilizando o layoutmanager no recyclerview

    }

    //Recuperar dados do banco de dados
    private fun loadAll(dao: restaurantDAO): MutableList<RestaurantBean>{
        return dao.getAll()
    }

    //Inserindo o arquivo de menu na toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return true
    }

    //Ação do botão da toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout_menu -> {
                FirebaseLogout()
            }
        }
        return true
    }

    //Ação de logout do usuário no firebase
    private fun FirebaseLogout(){
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }
}