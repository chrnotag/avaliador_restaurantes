package com.example.avaliador_restaurantes.utils

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

//Método usado para converter as imagens salvas no banco de dados
// em format ByteArray para um formato Drawable
class byteToDrawable(byte: ByteArray) {
    val image: Drawable =
        BitmapDrawable(BitmapFactory.decodeByteArray(byte, 0, byte.size))
    val drawable: Drawable
        get() {
            return image
        }
}