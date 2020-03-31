package com.example.proyecto5cuatri.activitys

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto5cuatri.R.*
import com.example.proyecto5cuatri.modelo.mapaModel
import com.example.proyecto5cuatri.modelo.publicacionModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_info_publicacion.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import android.text.method.Touch.onTouchEvent
import android.view.View.OnTouchListener

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.text.method.Touch.onTouchEvent

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.android.gms.maps.MapView


class info_publicacion : AppCompatActivity(), View.OnClickListener , OnMapReadyCallback  {


    private val publicacion = publicacionModel()
    private val mapa = mapaModel(this)
    private var ngoogle:GoogleMap ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_info_publicacion)
        var intent = intent.extras
        if (intent != null) {
            publicacion.id = intent.getInt("id", 0)
            publicacion.titulo = intent.getString("titulo", "")
            publicacion.empleada = intent.getString("empleada", "")
            publicacion.descripcion = intent.getString("descripcion","")
            publicacion.tarifa = intent.getString("tarifa","")
            publicacion.disponibilidad = intent.getString("disponibilidad","")
            publicacion.fecha = intent.getString("fecha", "")
            publicacion.extra = intent.getString("extra","")
            publicacion.radio = intent.getDouble("radio",0.0)
            publicacion.icono = intent.getString("icono","")
            publicacion.categoria= intent.getString("categoria","")
            publicacion.latitud = intent.getDouble("latitud", 0.0)
            publicacion.longitud = intent.getDouble("longitud",0.0)
            publicacion.fotoEmpleada = intent.getString("fotoEmpleada","")
            publicacion.telefono=intent.getString("telefono","")
            publicacion.score=intent.getDouble("score",0.0)
            tv_Titulo.text = publicacion.titulo
            //Picasso.with(this).load(publicacion.fotoEmpleada)
        }
        if (mapView_Info!=null) {
            mapView_Info.onCreate(null)
            mapView_Info.onResume()
            mapView_Info.getMapAsync(this)
        }

    }
    override fun onClick(v: View?) {

    }



    override fun onMapReady(google: GoogleMap?) {
        ngoogle = google
        mapa.estiloMapa(google)
        val place = LatLng(publicacion.latitud, publicacion.longitud)
        google!!.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 14F))
        google.uiSettings.setAllGesturesEnabled(false)
        val d = Base64.decode(publicacion.icono, Base64.DEFAULT)
        val bm = BitmapFactory.decodeByteArray(d, 0, d.size)
        google?.addMarker(
            MarkerOptions().position(
                LatLng(
                    publicacion.latitud,
                    publicacion.longitud
                )
            )
                .icon(BitmapDescriptorFactory.fromBitmap(bm))
        )
        google?.addCircle(
            CircleOptions().center(
                LatLng(
                    publicacion.latitud,
                    publicacion.longitud
                )
            ).radius(publicacion.radio).strokeWidth(4F).strokeColor(Color.BLUE).fillColor(Color.LTGRAY)
        )
        google.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener { true })

    }


}
