package com.example.avaliador_restaurantes.Adapter

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.avaliador_restaurantes.BEAN.RestaurantBean
import com.example.avaliador_restaurantes.DBAcess.DBAcess
import com.example.avaliador_restaurantes.R
import com.example.avaliador_restaurantes.activities.AdicionarRestaurante
import com.example.avaliador_restaurantes.activities.OpenRestaurantActivity
import com.example.avaliador_restaurantes.utils.byteToDrawable
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecyclerRestaurantAdapter(private val dados: MutableList<RestaurantBean>) :
    RecyclerView.Adapter<ViewHolder>() {

    private var header = 0 //Codigo de visualização para o cabeçalho
    private var body = 1 //Código de visualização para o corpo

    //classe ViewHolder para o corpo do recyclerview
    inner class viewHolder(itemview: View) : ViewHolder(itemview) {
        //Instancia dos elementos da UI da visualização
        var imgRestaurant: ImageView
        var nameRestaurant: TextView
        var star1: ImageView
        var star2: ImageView
        var star3: ImageView
        var star4: ImageView
        var star5: ImageView

        //Iniciação dos elementos da UI
        init {
            imgRestaurant = itemview.findViewById(R.id.img_restaurant_recycler)
            nameRestaurant = itemview.findViewById(R.id.name_restaurant)
            star1 = itemview.findViewById(R.id.star1_recycler)
            star2 = itemview.findViewById(R.id.star2_recycler)
            star3 = itemview.findViewById(R.id.star3_recycler)
            star4 = itemview.findViewById(R.id.star4_recycler)
            star5 = itemview.findViewById(R.id.star5_recycler)
        }
    }

    //Classe ViewHolder para o cabeçalho do recyclerview
    inner class headerHolder(itemview: View) : ViewHolder(itemview) {
        //Instancia dos elementos da UI da visualização
        var add_restaurant: FloatingActionButton

        //Iniciação dos elementos da UI
        init {
            add_restaurant = itemview.findViewById(R.id.button_add_restaurant)
        }

    }

    //Definição do tipo de visualização dependendo da posição do elemento no recyclerview
    //No caso estamos definindo que o item de posição 0 será o cabeçalho
    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> return header
            else -> return body
        }
    }

    /**
     * @param viewType - Usado para retornar o tipo de visualização definido na função getItemView Type
     * @return - está retornando a visualização na posição ao qual ela deva estar sendo exibida
     * no caso, na primeira posição (0), a qual será o nosso cabeçalho, ele também retorna
     * a visualização do restante do recyclerview que será preenchido com os dados do SQLite
     * e preenchera qualquer outra posição que não seja a 0.
     * **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        when (viewType) {
            header -> {
                view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.header_recycler, parent, false)
                return headerHolder(view)
            }
            else -> {
                view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.card_restaurant, parent, false)
                return viewHolder(view)
            }
        }

    }

    /**
     *@param holder - retorna a nossa ViewHolder e nos permite através dela resgatar o viewType,
     * com isso é possivel controlar os elementos de cada viewHolder separadamente.
     **/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            //View do cabeçalho
            header -> {

                val viewholder = holder as headerHolder

                holder.itemView.setOnClickListener {
                    holder.itemView.context.startActivity(
                        Intent(
                            holder.itemView.context,
                            AdicionarRestaurante::class.java
                        )
                    )
                }
                viewholder.add_restaurant.setOnClickListener {
                    holder.itemView.context.startActivity(
                        Intent(
                            holder.itemView.context,
                            AdicionarRestaurante::class.java
                        )
                    )
                }
            }
            //View com os cards dos restaurantes
            else -> {

                //passando a nossa classe viewHolder para o holder da classe
                val viewHolder: viewHolder = holder as viewHolder

                //Configurando os elementos da UI com os dados resgatados do BD
                viewHolder.imgRestaurant.setImageDrawable(byteToDrawable(dados[position - 1].imgUrl).drawable)
                viewHolder.nameRestaurant.text = dados[position - 1].nameRestaurant

                //Resgatando o valor da pontuação média do restaurante do BD
                val media = dados[position - 1].media

                //Configurando as estrelas baseado na média do BD
                if (media != null) {
                    if (media >= 0 && media <= 5) {
                        viewHolder.star1.setImageResource(R.drawable.star_border)
                        viewHolder.star2.setImageResource(R.drawable.star_border)
                        viewHolder.star3.setImageResource(R.drawable.star_border)
                        viewHolder.star4.setImageResource(R.drawable.star_border)
                        viewHolder.star5.setImageResource(R.drawable.star_border)
                    } else if (media!! > 6 && media < 10) {
                        viewHolder.star5.setImageResource(R.drawable.half_star)
                        viewHolder.star1.setImageResource(R.drawable.star_border)
                        viewHolder.star2.setImageResource(R.drawable.star_border)
                        viewHolder.star3.setImageResource(R.drawable.star_border)
                        viewHolder.star4.setImageResource(R.drawable.star_border)
                        viewHolder.star5.setImageResource(R.drawable.star_border)
                    } else if (media > 11 && media < 20) {
                        viewHolder.star1.setImageResource(R.drawable.star_full)
                        viewHolder.star2.setImageResource(R.drawable.star_border)
                        viewHolder.star3.setImageResource(R.drawable.star_border)
                        viewHolder.star4.setImageResource(R.drawable.star_border)
                        viewHolder.star5.setImageResource(R.drawable.star_border)
                    } else if (media > 21 && media < 30) {
                        viewHolder.star1.setImageResource(R.drawable.star_full)
                        viewHolder.star2.setImageResource(R.drawable.half_star)
                        viewHolder.star3.setImageResource(R.drawable.star_border)
                        viewHolder.star4.setImageResource(R.drawable.star_border)
                        viewHolder.star5.setImageResource(R.drawable.star_border)
                    } else if (media > 31 && media < 40) {
                        viewHolder.star1.setImageResource(R.drawable.star_full)
                        viewHolder.star2.setImageResource(R.drawable.star_full)
                        viewHolder.star3.setImageResource(R.drawable.star_border)
                        viewHolder.star4.setImageResource(R.drawable.star_border)
                        viewHolder.star5.setImageResource(R.drawable.star_border)
                    } else if (media > 41 && media < 50) {
                        viewHolder.star1.setImageResource(R.drawable.star_full)
                        viewHolder.star2.setImageResource(R.drawable.star_full)
                        viewHolder.star3.setImageResource(R.drawable.half_star)
                        viewHolder.star4.setImageResource(R.drawable.star_border)
                        viewHolder.star5.setImageResource(R.drawable.star_border)
                    } else if (media > 51 && media < 60) {
                        viewHolder.star1.setImageResource(R.drawable.star_full)
                        viewHolder.star2.setImageResource(R.drawable.star_full)
                        viewHolder.star3.setImageResource(R.drawable.star_full)
                        viewHolder.star4.setImageResource(R.drawable.star_border)
                        viewHolder.star5.setImageResource(R.drawable.star_border)
                    } else if (media > 61 && media < 70) {
                        viewHolder.star1.setImageResource(R.drawable.star_full)
                        viewHolder.star2.setImageResource(R.drawable.star_full)
                        viewHolder.star3.setImageResource(R.drawable.star_full)
                        viewHolder.star4.setImageResource(R.drawable.half_star)
                        viewHolder.star5.setImageResource(R.drawable.star_border)
                    } else if (media > 71 && media < 80) {
                        viewHolder.star1.setImageResource(R.drawable.star_full)
                        viewHolder.star2.setImageResource(R.drawable.star_full)
                        viewHolder.star3.setImageResource(R.drawable.star_full)
                        viewHolder.star4.setImageResource(R.drawable.star_full)
                        viewHolder.star5.setImageResource(R.drawable.star_border)
                    } else if (media > 81 && media < 90) {
                        viewHolder.star1.setImageResource(R.drawable.star_full)
                        viewHolder.star2.setImageResource(R.drawable.star_full)
                        viewHolder.star3.setImageResource(R.drawable.star_full)
                        viewHolder.star4.setImageResource(R.drawable.star_full)
                        viewHolder.star5.setImageResource(R.drawable.half_star)
                    } else if (media >= 91) {
                        viewHolder.star1.setImageResource(R.drawable.star_full)
                        viewHolder.star2.setImageResource(R.drawable.star_full)
                        viewHolder.star3.setImageResource(R.drawable.star_full)
                        viewHolder.star4.setImageResource(R.drawable.star_full)
                        viewHolder.star5.setImageResource(R.drawable.star_full)
                    }
                }

                //Evento de clique no card do restaurante para abrir a activity OpenRestaurantActivity
                //E passando a ela como atributo todos os itens da tabela RestaurantBean via INTENT
                viewHolder.itemView.setOnClickListener {
                    val intent = Intent(holder.itemView.context, OpenRestaurantActivity::class.java)
                    intent.putExtra("id", dados[position - 1].ID_Restaurant)
                    intent.putExtra("nomeRestaurante", dados[position - 1].nameRestaurant)
                    intent.putExtra("notaTotal", dados[position - 1].media)
                    intent.putExtra("notasGerente", dados[position - 1].notaGerente)
                    intent.putExtra("imgRestaurante", dados[position - 1].imgUrl)
                    intent.putExtra("logradouro", dados[position - 1].logradouro)
                    intent.putExtra("numero", dados[position - 1].numero)
                    intent.putExtra("bairro", dados[position - 1].bairro)
                    intent.putExtra("cidade", dados[position - 1].cidade)
                    holder.itemView.context.startActivity(intent)
                }
            }
        }
    }

    //Configurando o tamanho do recyclerview
    //O tamanho será igual a 1 somado com o numero de Linhas na tabela do BD
    override fun getItemCount(): Int = 1 + dados.size
}