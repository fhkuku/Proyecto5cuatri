package com.example.proyecto5cuatri.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.modelo.publicacionModel
import kotlinx.android.synthetic.main.item_post.view.*


class adpPublicaciones(context: Context, lista: List<publicacionModel>) :
    RecyclerView.Adapter<adpPublicaciones.MyViewHolder>() {

    val contex: Context? = context
    val lista: List<publicacionModel> = lista

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View
        val layout: LayoutInflater = LayoutInflater.from(contex)
        view = layout.inflate(R.layout.item_post, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /*
        holder.titulo.setText(lista.get(position).titulo)
        holder.categoria.setText(lista.get(position).categoria)
        holder.descripcion.setText(lista.get(position).descripcion)
        holder.direccion.setText( lista.get(position).direccion +" "+lista.get(position).municipio +" "+lista.get(position).estado + " "+lista.get(position).pais)
        holder.fecha.setText(lista.get(position).fecha)
        holder.tarifa.setText("Costo del servicio : "+lista.get(position).tarifa)
        holder.nombre.setText( lista.get(position).nombre +" "+ lista.get(position).apellido_p )
        Picasso.with(contex).load(lista[position].imagen).into(holder.imagen)

         */
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo = itemView.txtTitulo
        val categoria = itemView.txtCategoria
        val descripcion = itemView.txtdescripcion
        val direccion = itemView.txtDireccion
        val fecha = itemView.txtpost_fecha
        val tarifa = itemView.txtTarifa
        val nombre = itemView.txtnombre
        val imagen = itemView.imgUser

    }

}
