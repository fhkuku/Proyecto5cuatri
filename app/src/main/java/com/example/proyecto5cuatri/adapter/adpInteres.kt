package com.example.proyecto5cuatri.adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.opengl.Visibility
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.modelo.interesModel
import com.example.proyecto5cuatri.modelo.validationModel
import kotlinx.android.synthetic.main.item_interes.view.*


class adpInteres(context: Context?=null, lista: List<interesModel>? =null, rcv:Int) :
    RecyclerView.Adapter<adpInteres.MyViewHolder>() {
    private val context: Context? = context
    private val lista: List<interesModel>? = lista
    private var row_index: Int = -1
    private var rcv:Int? = rcv
    private var validacion = validationModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val layout: LayoutInflater = LayoutInflater.from(context)
        view = layout.inflate(if (rcv == 1) R.layout.item_interes else R.layout.item_interes_mini, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        validacion.preferencia_color = context!!.getSharedPreferences(validacion.PREF_COLOR, Context.MODE_PRIVATE)

        holder.txtNombreInteres.text = lista!![position].nombre
        val d = Base64.decode(lista!![position].icono, Base64.DEFAULT)
        val bm = BitmapFactory.decodeByteArray(d, 0, d.size)
        holder.img.setImageBitmap(bm)
        holder.card_action.setOnClickListener {
            val intent = Intent("mensaje-id")
            intent.putExtra("id", lista!![position].id)
            LocalBroadcastManager.getInstance(this!!.context!!).sendBroadcast(intent)
            row_index = position
            notifyDataSetChanged()
        }
        if (row_index == position) {
            holder.card_action.setCardBackgroundColor(Color.parseColor("#FF4081"))
            holder.txtNombreInteres.setTextColor(Color.parseColor("#FFFFFF"))
        } else {
            holder.card_action.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.txtNombreInteres.setTextColor(Color.parseColor("#FF4081"))
        }
        if (validacion!!.preferencia_color!!.getString("color", "").toString()== lista!![position].id){
            holder.card_action.setCardBackgroundColor(Color.parseColor("#FF4081"))
            holder.txtNombreInteres.setTextColor(Color.parseColor("#FFFFFF"))
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombreInteres = itemView.txtNombreInteres
        val card_action = itemView.cardview
        val img:ImageView = itemView.imageView
    }

}