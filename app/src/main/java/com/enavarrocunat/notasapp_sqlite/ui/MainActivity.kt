package com.enavarrocunat.notasapp_sqlite.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.enavarrocunat.notasapp_sqlite.R
import com.enavarrocunat.notasapp_sqlite.dao.NotasDatabaseHelper
import com.enavarrocunat.notasapp_sqlite.databinding.ActivityMainBinding
import com.enavarrocunat.notasapp_sqlite.ui.adapters.NotasAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NotasDatabaseHelper
    private lateinit var notasAdapter: NotasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
    }

    override fun onResume() {
        super.onResume()
        notasAdapter.refreshList(db.getAllNotas())
    }

    private fun initView() {
        db = NotasDatabaseHelper(this)

        notasAdapter = NotasAdapter(db.getAllNotas(), this)

        binding.rvNotas.layoutManager = LinearLayoutManager(this)
        binding.rvNotas.adapter = notasAdapter

        binding.fabAgregarNota.setOnClickListener {
            startActivity(Intent(applicationContext, AgregarNotaActivity::class.java))
        }
    }
}