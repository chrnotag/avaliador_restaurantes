package com.example.avaliador_restaurantes.utils

import java.text.DecimalFormat

//Método utilizado para formatar as casas decimais dos valores Double das médias de avaliação
class formatDecimal(var value: Double) {

    val df = DecimalFormat("#.##")

    val format: Double
        get() = df.format(value).toDouble()
}