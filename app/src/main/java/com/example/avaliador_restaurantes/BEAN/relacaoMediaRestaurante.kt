package com.example.avaliador_restaurantes.BEAN

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

//Tabela relacaoMediaRestaurante usada para relacionar as tabelas RestaurantBean e mediaGeralBEAN
@Entity(tableName = "relacaoMediaRestaurante")
data class relacaoMediaRestaurante(
    @Embedded val restaurante: RestaurantBean,
    @Relation(
        parentColumn = "ID_Restaurant",
        entityColumn = "id_media",
        entity = mediaGeralBEAN::class
    )
    val media: List<mediaGeralBEAN>
)