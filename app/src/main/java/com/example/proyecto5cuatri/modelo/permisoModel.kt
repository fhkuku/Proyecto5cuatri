package com.example.proyecto5cuatri.modelo

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat

class permisoModel(context: Context) {
    var context = context
     fun permisosMensaje() {
        val builder = AlertDialog.Builder(context)
         builder.setTitle("Permisos obligatorios")
         builder.setMessage("Debe autorizar los permisos para el correcto funcionamiento de la aplicación")
         builder.setCancelable(false)
         builder.setPositiveButton("Aceptar") { dialog, which ->
             permisosObligatorios()
         }
         builder.show()
    }

    fun permisosObligatorios(){
        val permisoUbicacion: Int =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val permisoCamara:Int  = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        val permisoEscritura: Int =
            ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permisoLectura: Int =
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        val permisoUbicacionCoarse: Int = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permisoUbicacion == PackageManager.PERMISSION_GRANTED && permisoEscritura == PackageManager.PERMISSION_GRANTED && permisoLectura == PackageManager.PERMISSION_GRANTED && permisoUbicacionCoarse == PackageManager.PERMISSION_GRANTED && permisoCamara == PackageManager.PERMISSION_GRANTED){

        }else{
            requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CAMERA
                ),
                100
            )
        }
    }

    internal var permisos: OnRequestPermissionsResultCallback =
        object : OnRequestPermissionsResultCallback {
            override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
                this.onRequestPermissionsResult(requestCode, permissions, grantResults)
                if (requestCode == 100) {
                    if (grantResults.size == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED && grantResults[4] == PackageManager.PERMISSION_GRANTED) {

                    } else {
                        permisosObligatorios()
                    }
                }
            }
        }

}