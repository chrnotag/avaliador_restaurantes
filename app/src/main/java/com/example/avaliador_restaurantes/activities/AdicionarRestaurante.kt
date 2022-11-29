package com.example.avaliador_restaurantes.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.room.Room
import com.example.avaliador_restaurantes.BEAN.RestaurantBean
import com.example.avaliador_restaurantes.DAO.restaurantDAO
import com.example.avaliador_restaurantes.DBAcess.DBAcess
import com.example.avaliador_restaurantes.R
import com.example.avaliador_restaurantes.databinding.ActivityAdicionarRestauranteBinding
import com.google.firebase.auth.FirebaseAuth
import java.io.ByteArrayOutputStream

class AdicionarRestaurante : AppCompatActivity() {

    private val binding by lazy {
        ActivityAdicionarRestauranteBinding.inflate(layoutInflater)
    }

    // Códigos dos pedidos
    companion object {
        private const val PERMISSION_CODE = 1
    }

    // Variável de chacagem de permissão
    private var check = false

    //Variável para guardar os bytes da imagem de perfil
    private var byteImg: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Iniciando o Banco de dados
        val db = DBAcess.getDatabase(this)
        //Instancia do DAO com o banco de dados
        val restaurantDAO = db.restauranteDAO()

        //Configurações da Toolbar
        val toolbar = supportActionBar!!
        toolbar.setHomeButtonEnabled(true)
        toolbar.setDisplayHomeAsUpEnabled(true)
        toolbar.title = ""

        //Ação do botão Salvar
        binding.salvarRestauranteButton.setOnClickListener {
            salvarDados(restaurantDAO)
            returnHome()
        }

        //Ação do click na imagem de perfil
        binding.imgPerfilSalvarRestaurante.setOnClickListener {
            if (check)
                openGalleryForImage()
            else {
                initPermissions()
                if (check)
                    openGalleryForImage()
            }
        }
    }


    // Recebe resultados da intent de ACTION_PICK
    private val startImageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            binding.imgPerfilSalvarRestaurante.setImageURI(it.data?.data)
            convertToBAOS()
        }
    }

    //Converte a imagem de perfil para um ByteArray para salvar no banco de dados
    private fun convertToBAOS() {
        val baos = ByteArrayOutputStream()
        val bitmap = binding.imgPerfilSalvarRestaurante.drawable.toBitmap()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        byteImg = baos.toByteArray()
    }

    //Salva o restaurante no banco de dados
    private fun salvarDados(dao: restaurantDAO) {
        val dados = RestaurantBean(
            imgUrl = byteImg!!,
            nameRestaurant = binding.nomeRestauranteSalvar.text.toString(),
            notaGerente = binding.notasGerenteSalvarRestaurante.text.toString(),
            logradouro = binding.logradouroSalvarRestaurante.text.toString(),
            numero = binding.numeroSalvarRestaurante.text.toString(),
            bairro = binding.bairroSalvarRestaurante.text.toString(),
            cidade = binding.cidadeSalvarRestaurante.text.toString(),
            media = null

        )
        dao.insertAll(dados)
    }

    // Abre Intent de ACTION_PICK
    private fun openGalleryForImage() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )
        startImageResult.launch(intent)
    }

    // Checa se existe ou não permissão
    private fun initPermissions() {
        if (!getPermission()) setPermission()
        else check = true
    }

    // Checa se existe permissão
    private fun getPermission(): Boolean =
        (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)

    // Pede permissão se não tiver
    private fun setPermission() {
        val permissionsList = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        ActivityCompat.requestPermissions(this, permissionsList.toTypedArray(), PERMISSION_CODE)
    }

    // Envia mensagem por não ter permissão
    private fun errorPermission() {
        Toast.makeText(this, R.string.no_permission, Toast.LENGTH_SHORT).show()
    }

    // Recebe resultado do pedido de permissão
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    errorPermission()
                } else {
                    check = true
                    openGalleryForImage()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //Insere a ação de voltar a tela anterior no botão da toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                returnHome()
            }
        }
        return true
    }

    //Reconfigura a ação do botão de voltar do android para iniciar outra activity
    override fun onBackPressed() {
        super.onBackPressed()
        returnHome()
    }

    //Inicia a Activity do MainScreem e limpa a pilha de activities
    private fun returnHome() {
        startActivity(Intent(this, MainScreen::class.java))
        finishAffinity()
    }

}