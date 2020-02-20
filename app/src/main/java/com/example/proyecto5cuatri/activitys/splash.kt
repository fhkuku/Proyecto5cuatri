package com.example.proyecto5cuatri.activitys

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.modelo.validationModel
import kotlinx.android.synthetic.main.activity_splash.*


class splash : AppCompatActivity() {

    val validacion = validationModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val animation = AnimationUtils.loadAnimation(this, R.anim.zoom)
        imgSplash.startAnimation(animation)
        var intent:Intent? = null
        validacion.preferencias = getSharedPreferences(validacion.PREF_USUARIO, Context.MODE_PRIVATE)

            val timer = object : Thread() {
                override fun run() {
                    try {
                        Thread.sleep(3000)

                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    } finally {
                        if(validacion.preferencias!!.contains("id")) {
                        intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        }else{
                            intent = Intent(applicationContext, login::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
            timer.start()
    }
}
