package com.example.proyecto5cuatri.modelo

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.activitys.ui.Home
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions

class mapaModel(context: Context) {
    private var context = context
    internal fun estiloMapa(googleMap: GoogleMap?) {
        try {
            val succes:Boolean = googleMap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.s))
            if(!succes){
            }
        } catch (e: Exception) {

        }
    }
    internal fun gpsNotEnabled(manager:LocationManager){
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Su gps no esta encendido, es necesario activarlo para el correcto funcionamiento de la aplicación")
        builder.setCancelable(false).setPositiveButton("SI") { _: DialogInterface, _: Int ->
            context.startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }.create()
        builder.show()
    }
}