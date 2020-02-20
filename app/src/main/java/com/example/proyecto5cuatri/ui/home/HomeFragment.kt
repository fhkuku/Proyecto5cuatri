package com.example.proyecto5cuatri.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.modelo.mapaModel
import com.example.proyecto5cuatri.modelo.permisoModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(), OnMapReadyCallback {

    val mapaModel = mapaModel(this)
    var permisos: permisoModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
         permisos = permisoModel(root.context)
        if (root.mapPublicacion != null) {
            root.mapPublicacion.onCreate(null)
            root.mapPublicacion.onResume()
            root.mapPublicacion.getMapAsync(this)
        }
        return root
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        try {
            Permisos()
            val merida = LatLng(20.9753700, -89.6169600)
            mapaModel.estiloMapa(googleMap)
            googleMap!!.isMyLocationEnabled = true
            googleMap!!.uiSettings.isMyLocationButtonEnabled = true
            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(merida, 10F))
        }catch (e:Exception){
        }
    }

    private fun Permisos(){
        val permisoUbicacion =
            ContextCompat.checkSelfPermission(this.context!!, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permisoUbicacion == PackageManager.PERMISSION_GRANTED) {
        }else {
            permisos!!.permisosMensaje()
        }
    }
}