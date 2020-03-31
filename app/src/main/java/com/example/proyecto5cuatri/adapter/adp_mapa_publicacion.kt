package com.example.proyecto5cuatri.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.modelo.publicacionModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.item_mapa_publicacion.view.*

class adp_mapa_publicacion(context: Context?): GoogleMap.InfoWindowAdapter{

    private var context = context
    private var view:View? = null


    override fun getInfoContents(marker: Marker?): View? {
        val infowindow = marker?.tag as publicacionModel
        view = (context as Activity).layoutInflater.inflate(R.layout.item_mapa_publicacion, null)

        view!!.txtTitulo.text = infowindow.titulo
        view!!.txtdisposision.text = infowindow.disponibilidad
        view!!.txttarifa.text = infowindow.tarifa

        return view
    }

    override fun getInfoWindow(marker: Marker?): View? {

        return null
    }

}