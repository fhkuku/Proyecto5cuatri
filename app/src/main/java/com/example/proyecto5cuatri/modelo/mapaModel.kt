package com.example.proyecto5cuatri.modelo

import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.ui.home.HomeFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions

class mapaModel(context: HomeFragment) {
    var context:HomeFragment = context

    fun estiloMapa(googleMap: GoogleMap?) {
        try {
            val succes:Boolean = googleMap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(context.context, R.raw.s))
            if(!succes){
            }
        } catch (e: Exception) {

        }
    }
}