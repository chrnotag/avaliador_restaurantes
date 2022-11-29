package com.example.avaliador_restaurantes.DBAcess

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.avaliador_restaurantes.BEAN.RestaurantBean
import com.example.avaliador_restaurantes.BEAN.mediaGeralBEAN
import com.example.avaliador_restaurantes.BEAN.relacaoMediaRestaurante
import com.example.avaliador_restaurantes.DAO.mediaGeralDAO
import com.example.avaliador_restaurantes.DAO.restaurantDAO

/**
 * Criação do banco de dados e instanciação unica utilizando o método Singleton
 * para sempre chamar a mesma instancia do banco de dados ao invés de criar varias chamadas.
 */
@Database(entities = [RestaurantBean::class, mediaGeralBEAN::class], version = 1)
abstract class DBAcess : RoomDatabase() {

    abstract fun restauranteDAO(): restaurantDAO //Configuração do metodo DAO do restaurant
    abstract fun mediaGeralDAO(): mediaGeralDAO //Configuração do metodo DAO da médiaGeral

    //Configuração do Singleton
    companion object {
        @Volatile
        private var INSTANCE: DBAcess? = null

        fun getDatabase(context: Context): DBAcess {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DBAcess::class.java,
                    "restaurantDB"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}