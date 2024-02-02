package com.example.yemek

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "YemekDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Yemekler"
        private const val COLUMN_ID = "id"
        private const val COLUMN_YEMEK_ISMI = "yemekIsmi"
        private const val COLUMN_YEMEK_TARIFI = "yemekTarifi"
        private const val COLUMN_YEMEK_RESIM = "yemekResim"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableSQL = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_YEMEK_ISMI TEXT, "
                + "$COLUMN_YEMEK_TARIFI TEXT, "
                + "$COLUMN_YEMEK_RESIM BLOB)")
        db.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addYemek(yemekIsmi: String, yemekTarifi: String, yemekResim: ByteArray) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_YEMEK_ISMI, yemekIsmi)
            put(COLUMN_YEMEK_TARIFI, yemekTarifi)
            put(COLUMN_YEMEK_RESIM, yemekResim)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    @SuppressLint("Range")
    fun getAllYemekler(): ArrayList<Yemek> {
        val yemekler = ArrayList<Yemek>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val yemekIsmi = cursor.getString(cursor.getColumnIndex(COLUMN_YEMEK_ISMI))
            val yemekTarifi = cursor.getString(cursor.getColumnIndex(COLUMN_YEMEK_TARIFI))
            val yemekResim = cursor.getBlob(cursor.getColumnIndex(COLUMN_YEMEK_RESIM))

            val yemek = Yemek(yemekIsmi, yemekTarifi, yemekResim)
            yemek.resimBitmap = BitmapFactory.decodeByteArray(yemekResim, 0, yemekResim.size)
            yemekler.add(yemek)
        }

        cursor.close()
        return yemekler
    }
}