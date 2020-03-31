package com.example.proyecto5cuatri.modelo

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.proyecto5cuatri.activitys.MainActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class permisoModel(context: Context) {

    private var context = context
    private val validacion = validationModel()
    private val actividades = activityModel(context)
    internal fun permisosValidacion(){
        validacion.preferencias_permiso = context.getSharedPreferences(validacion.PREF_PERMISO, Context.MODE_PRIVATE)
        val share = validacion!!.preferencias_permiso?.edit()
        Dexter.withActivity(context as Activity?)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if(report.areAllPermissionsGranted()){
                        share?.putString("crear", "creado")
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        share?.commit()
                        (context as Activity).finish()
                    }else{
                        Toast.makeText(context, "No se han autorizado todos los permisos", Toast.LENGTH_LONG).show()
                    }
                    if(report.isAnyPermissionPermanentlyDenied){
                        val builder = android.app.AlertDialog.Builder(context)
                        builder.setTitle("Permiso denegado")
                        builder.setMessage("Algunos permisos fueron denegados permanentemente, es necesario ir ajustes para activar el permiso.")
                            .setNegativeButton("Cancelar", null)
                            .setPositiveButton("Aceptar") { dialog, which ->
                                val intent = Intent()
                                intent.action= Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                intent.data = Uri.fromParts("package",context.packageName, null)
                                context.startActivity(intent)
                            }.show()
                    }
                }
                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token?.continuePermissionRequest()
                }
            }).withErrorListener{
                actividades.showMensajeToast(it.name)
            }.check()
    }

    internal fun validarPermisoSplash(){
        val permisoUbicacion =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionCheck =
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
        val permisoEscritura =
            ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permisoLectura =
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        val permisoCoarseUbicacion = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED && permisoEscritura == PackageManager.PERMISSION_GRANTED && permisoLectura == PackageManager.PERMISSION_GRANTED && permisoUbicacion == PackageManager.PERMISSION_GRANTED && permisoCoarseUbicacion == PackageManager.PERMISSION_GRANTED) {
            actividades.menu()
            (context as Activity).finish()
        } else {
            actividades.permiso()
            (context as Activity).finish()
        }
    }

    internal fun validarPermisoAcvityPermiso(){
        val permisoUbicacion =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionCheck =
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
        val permisoEscritura =
            ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permisoLectura =
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        val permisoCoarseUbicacion = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED && permisoEscritura == PackageManager.PERMISSION_GRANTED && permisoLectura == PackageManager.PERMISSION_GRANTED && permisoUbicacion == PackageManager.PERMISSION_GRANTED && permisoCoarseUbicacion == PackageManager.PERMISSION_GRANTED) {
            actividades.menu()
            (context as Activity).finish()
        } else {

        }
    }
}