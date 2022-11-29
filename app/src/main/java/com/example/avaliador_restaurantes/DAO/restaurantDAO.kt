package com.example.avaliador_restaurantes.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.avaliador_restaurantes.BEAN.RestaurantBean

//Método DAO para o controle da tabela Restaurant
@Dao
interface restaurantDAO {

    @Query("SELECT * FROM restaurantInfo")
    fun getAll(): MutableList<RestaurantBean>

    //Query para buscar o restaurante por nome (não está sendo utilizado no aplicativo)
    @Query("SELECT * FROM restaurantInfo WHERE nomeRestaurant = :arg0")
    fun loadByName(arg0: String): MutableList<RestaurantBean>

    //Método usado para atualizar a tabela com os dados informados pela classe
    @Query("UPDATE restaurantInfo SET media_nota = :media WHERE ID_Restaurant LIKE :id")
    fun updateMedia(media: Double, id: Long)

    @Insert
    fun insertAll(vararg restaurante: RestaurantBean)
}