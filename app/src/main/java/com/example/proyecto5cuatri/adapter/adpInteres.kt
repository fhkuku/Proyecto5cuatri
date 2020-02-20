package com.example.proyecto5cuatri.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.modelo.interesModel
import kotlinx.android.synthetic.main.item_interes.view.*


class adpInteres(context: Context, lista: List<interesModel>) :
    RecyclerView.Adapter<adpInteres.MyViewHolder>() {
    val context: Context? = context
    val lista: List<interesModel> = lista
    var row_index: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val layout: LayoutInflater = LayoutInflater.from(context)
        view = layout.inflate(R.layout.item_interes, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtNombreInteres.text = lista.get(position).nombre
        holder.card_action.setOnClickListener {
            val intent = Intent("mensaje-id")
            intent.putExtra("id", lista.get(position).id)
            LocalBroadcastManager.getInstance(this!!.context!!).sendBroadcast(intent)
            row_index = position
            notifyDataSetChanged()

        }
        if (row_index == position) {
            holder.card_action.setBackgroundColor(Color.parseColor("#FF4081"))
            holder.txtNombreInteres.setTextColor(Color.parseColor("#FFFFFF"))
        } else {
            holder.card_action.setBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.txtNombreInteres.setTextColor(Color.parseColor("#FF4081"))
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombreInteres = itemView.txtNombreInteres
        val card_action = itemView.cardView
    }

}