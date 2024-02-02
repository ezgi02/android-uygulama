package com.example.yemek

import android.graphics.Bitmap

class Yemek(val yemekIsmi: String, val yemekTarifi: String, val yemekResim: ByteArray){
    var resimBitmap: Bitmap? = null
}
