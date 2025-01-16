package com.enavarrocunat.notasapp_sqlite.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.enavarrocunat.notasapp_sqlite.R
import com.enavarrocunat.notasapp_sqlite.dao.NotasDatabaseHelper
import com.enavarrocunat.notasapp_sqlite.data.Nota
import com.enavarrocunat.notasapp_sqlite.databinding.ActivityAgregarNotaBinding

class AgregarNotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarNotaBinding
    private lateinit var db: NotasDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarNotaBinding.inflate(layoutInflater)
        //enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = NotasDatabaseHelper(this)

        setClickListener()
    }

    private fun setClickListener() {
        binding.ibGuardarNota.setOnClickListener {
            val titulo = binding.etTitulo.text.toString()
            val descripcion = binding.etDescripcion.text.toString()
           if(titulo.isNotBlank() && descripcion.isNotBlank()){
               guardarNota(titulo, descripcion)
           }else{
               Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show()
           }
        }
    }

    private fun guardarNota(titulo: String, descripcion: String) {
            val nota = Nota(
                id = 0,
                titulo = titulo,
                descripcion = descripcion
            )
            db.insertNota(nota)

            startActivity(Intent(applicationContext, MainActivity::class.java))
            finishAffinity()
            Toast.makeText(this, "Nota guardada correctamente", Toast.LENGTH_SHORT).show()
    }
}