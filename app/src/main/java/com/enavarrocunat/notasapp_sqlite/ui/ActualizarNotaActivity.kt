package com.enavarrocunat.notasapp_sqlite.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.enavarrocunat.notasapp_sqlite.R
import com.enavarrocunat.notasapp_sqlite.dao.NotasDatabaseHelper
import com.enavarrocunat.notasapp_sqlite.data.Nota
import com.enavarrocunat.notasapp_sqlite.databinding.ActivityActualizarNotaBinding

class ActualizarNotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityActualizarNotaBinding
    private lateinit var db: NotasDatabaseHelper
    private lateinit var nota: Nota

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityActualizarNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = NotasDatabaseHelper(this)
        getIntentData()

    }

    private fun getIntentData() {
        val idNota = intent.getIntExtra("id_nota", -1)
        if (idNota == -1) {
            this.finish()
        } else {
            getNota(idNota)
        }
    }

    private fun getNota(idNota: Int) {
        nota = db.getNotaById(idNota)
        binding.etTitulo.setText(nota.titulo)
        binding.etDescripcion.setText(nota.descripcion)
        binding.ivActualizarNota.setOnClickListener{
            val tituloNuevo = binding.etTitulo.text.toString()
            val descripcionNueva = binding.etDescripcion.text.toString()
            val notaActualizada = Nota(
                id = nota.id,
                titulo = tituloNuevo,
                descripcion = descripcionNueva)
            db.updateNota(notaActualizada)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}