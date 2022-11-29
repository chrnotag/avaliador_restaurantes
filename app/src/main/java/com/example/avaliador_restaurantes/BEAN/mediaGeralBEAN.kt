package com.example.avaliador_restaurantes.BEAN

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//Tabela mediaGeral usada para salvar as escolhas do usuário atual e a média total votada por ele
@Entity(tableName = "mediaGeral")
data class mediaGeralBEAN(
    @PrimaryKey(autoGenerate = true) val id_media: Long = 0,
    @ColumnInfo(name = "restaurant_id") var restaurant_id: Long,
    @ColumnInfo(name = "uidUser") var uidUser: String,
    @ColumnInfo(name = "media") var media: Double,
    @ColumnInfo(name = "Q1") var Q1: Int?,
    @ColumnInfo(name = "Q2") var Q2: Int?,
    @ColumnInfo(name = "Q3") var Q3: Int?,
    @ColumnInfo(name = "Q4") var Q4: Int?,
    @ColumnInfo(name = "Q5") var Q5: Int?,
    @ColumnInfo(name = "Q6") var Q6: Int?
)