package com.example.avaliador_restaurantes.BEAN

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

//Tabela usada para salvar todos os dados referentes ao restaurante e também sua média geral
@Entity(tableName = "restaurantInfo")
data class RestaurantBean(
    @PrimaryKey(autoGenerate = true) var ID_Restaurant: Long = 0,
    @ColumnInfo(name = "imgRestaurant") var imgUrl: ByteArray,
    @ColumnInfo(name = "nomeRestaurant") var nameRestaurant: String,
    @ColumnInfo(name = "notasGerente") var notaGerente: String,
    @ColumnInfo(name = "logradouro") var logradouro: String,
    @ColumnInfo(name = "numero") var numero: String,
    @ColumnInfo(name = "bairro") var bairro: String,
    @ColumnInfo(name = "cidade") var cidade: String,
    @ColumnInfo(name = "media_nota") var media: Double?
)