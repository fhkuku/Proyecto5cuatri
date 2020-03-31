package com.example.proyecto5cuatri.activitys

import android.content.Context
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.modelo.activityModel
import com.example.proyecto5cuatri.modelo.permisoModel
import com.example.proyecto5cuatri.modelo.validationModel
import kotlinx.android.synthetic.main.activity_splash.*


class splash : AppCompatActivity() {

    private val validacion = validationModel()
    private val permiso = permisoModel(this)
    private val actividades = activityModel(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val animation = AnimationUtils.loadAnimation(this, R.anim.zoom)
        imgSplash.startAnimation(animation)
        validacion.preferencias_user = getSharedPreferences(validacion.PREF_USUARIO, Context.MODE_PRIVATE)
        validacion.preferencias_permiso = getSharedPreferences(validacion.PREF_PERMISO, Context.MODE_PRIVATE)
            val timer = object : Thread() {
                override fun run() {
                    try {
                        sleep(3000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    } finally {
                        if(validacion.preferencias_user!!.contains("id")) {
                            if (validacion.preferencias_permiso!!.contains("crear")){
                                permiso.validarPermisoSplash()
                            }else{
                                actividades.permiso()
                                finish()
                            }
                        }else{
                            actividades.login()
                            finish()
                        }
                    }
                }
            }
            timer.start()
    }
}
