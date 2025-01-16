package com.enavarrocunat.notasapp_sqlite.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.enavarrocunat.notasapp_sqlite.data.Nota

class NotasDatabaseHelper (context: Context): SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "notas.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "notas"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITULO = "titulo"
        private const val COLUMN_DESCRIPCION = "descripcion"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITULO TEXT, $COLUMN_DESCRIPCION TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNota(nota: Nota){
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_TITULO, nota.titulo)
            put(COLUMN_DESCRIPCION, nota.descripcion)
        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllNotas(): List<Nota> {
        val listaNotas = mutableListOf<Nota>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
            val nota = Nota(id, titulo, descripcion)
            listaNotas.add(nota)
        }
        cursor.close()
        db.close()
        return listaNotas
    }

    fun getNotaById(id: Int): Nota {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $id"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val nota = Nota(
            id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
            descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
        )
        cursor.close()
        db.close()
        return nota
    }

    fun updateNota(nota: Nota) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITULO, nota.titulo)
            put(COLUMN_DESCRIPCION, nota.descripcion)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(nota.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
   }

    fun deleteNota(id:Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(id.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}