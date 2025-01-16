package com.enavarrocunat.notasapp_sqlite.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.enavarrocunat.notasapp_sqlite.R
import com.enavarrocunat.notasapp_sqlite.dao.NotasDatabaseHelper
import com.enavarrocunat.notasapp_sqlite.data.Nota
import com.enavarrocunat.notasapp_sqlite.ui.ActualizarNotaActivity

class NotasAdapter (private var listaNotas: List<Nota>, context: Context): RecyclerView.Adapter<NotasAdapter.NotaViewHolder>()  {

    private val db = NotasDatabaseHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nota, parent, false)
        return NotaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaNotas.size
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = listaNotas[position]
        holder.itemTitulo.text = nota.titulo
        holder.itemDescripcion.text = nota.descripcion

        setOnClickListeners(holder, nota)
    }

    private fun setOnClickListeners(
        holder: NotaViewHolder,
        nota: Nota
    ) {
        holder.ivActualizar.setOnClickListener {
            val intent = Intent(holder.itemView.context, ActualizarNotaActivity::class.java).apply {
                putExtra("id_nota", nota.id)
            }

            holder.itemView.context.startActivity(intent)
        }
        holder.ivEliminar.setOnClickListener {
            db.deleteNota(nota.id)
            refreshList(db.getAllNotas())
            Toast.makeText(holder.itemView.context, "Nota eliminada", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshList(nuevaLista : List<Nota>){
        listaNotas = nuevaLista
        notifyDataSetChanged()
    }

    class NotaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemTitulo : TextView = itemView.findViewById(R.id.item_titulo)
        val itemDescripcion : TextView = itemView.findViewById(R.id.item_descripcion)
        val ivActualizar : ImageView = itemView.findViewById(R.id.iv_actualizar)
        val ivEliminar : ImageView = itemView.findViewById(R.id.iv_eliminar)
    }

}