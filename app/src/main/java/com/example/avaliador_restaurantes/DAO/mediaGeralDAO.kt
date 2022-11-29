package com.example.avaliador_restaurantes.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.avaliador_restaurantes.BEAN.mediaGeralBEAN
import com.example.avaliador_restaurantes.BEAN.relacaoMediaRestaurante

//Método DAO usado para o controle da tabela mediaGeralBEAN
@Dao
interface mediaGeralDAO {

    //Query usada para fazer consulta nas tabelas mediaGeral e Restaurant
    //Usando o relacionamento um para muitos
    @Transaction
    @Query("SELECT * FROM restaurantInfo")
    fun getAllMediaAndRestaurant(): MutableList<relacaoMediaRestaurante>

    @Insert
    fun insertAll(vararg mediaGeral: mediaGeralBEAN)

    @Query("SELECT * FROM mediaGeral")
    fun getAllMedia(): MutableList<mediaGeralBEAN>

    //Query usada para resgatar o UID do usuário que votou no restaurante especificado
    @Query("SELECT uidUser FROM mediaGeral WHERE restaurant_id LIKE :restauranteId")
    fun getAvaliationUser(restauranteId: Long): MutableList<String>

    //Query usada para resgatar os dados da avaliacao do usuário de um restaurante especifico
    @Query("SELECT * FROM mediaGeral WHERE restaurant_id = :arg0 AND uidUser = :arg1")
    fun getAvaliacao(arg0: Long, arg1: String): MutableList<mediaGeralBEAN>
}