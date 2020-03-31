package com.example.proyecto5cuatri.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.activitys.info_publicacion
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
        if (!lista[position].visible){
            holder.itemView!!.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0,0)
        }
        holder.tv_Titulo.text = lista[position].titulo
        holder.tv_descripcion.text = lista[position].descripcion
        holder.tv_tarifa.text = "Precio $"+ lista[position].tarifa+" MXN"
        holder.btn_solicitar.setOnClickListener{
            val intent = Intent(contex, info_publicacion::class.java)
            intent.putExtra("id", lista[position].id)
            intent.putExtra("empleada", lista[position].empleada)
            intent.putExtra("descripcion", lista[position].descripcion)
            intent.putExtra("tarifa", lista[position].tarifa)
            intent.putExtra("disponibilidad", lista[position].disponibilidad)
            intent.putExtra("titulo", lista[position].titulo)
            intent.putExtra("fecha", lista[position].fecha)
            intent.putExtra("extra", lista[position].extra)
            intent.putExtra("latitud", lista[position].latitud)
            intent.putExtra("longitud", lista[position].longitud)
            intent.putExtra("radio", lista[position].radio)
            intent.putExtra("icono", lista[position].icono)
            intent.putExtra("categoria",lista[position].categoria)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }else{
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            contex?.startActivity(intent)
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_Titulo = itemView.tv_Titulo
        val tv_descripcion = itemView.tv_descripcion
        val tv_tarifa = itemView.tv_tarifa
        val btn_solicitar = itemView.btnsolicitar


    }

}
