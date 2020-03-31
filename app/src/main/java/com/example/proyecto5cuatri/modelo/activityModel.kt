package com.example.proyecto5cuatri.modelo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.proyecto5cuatri.activitys.MainActivity
import com.example.proyecto5cuatri.activitys.permisos

class activityModel(context: Context) {
    private var context = context
    private var intent:Intent? = null
    private val validacion = validationModel()
    internal fun login(){
        intent = Intent(context, com.example.proyecto5cuatri.activitys.login::class.java)
        context.startActivity(intent)
    }
    internal fun registro(){
        val intent = Intent(context, com.example.proyecto5cuatri.activitys.registro::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)
    }
    internal fun permiso(){
        intent = Intent(context, permisos::class.java)
        context.startActivity(intent)
    }
    internal fun registroInteres(){

    }
    internal fun splash(){
    }
    internal fun menu(){
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
    internal fun showMensajeToast(mensaje:String){
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }
    internal fun validarSesion(){
        validacion.preferencias_user = context.getSharedPreferences(validacion.PREF_USUARIO, Context.MODE_PRIVATE)
        if (validacion!!.preferencias_user!!.contains("id")){
        }else{
            val editor = validacion!!.preferencias_user?.edit()
            editor?.clear()?.commit()
            login()
            (context as Activity).finish()
        }

    }
}