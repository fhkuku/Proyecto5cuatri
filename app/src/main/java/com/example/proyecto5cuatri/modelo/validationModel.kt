package com.example.proyecto5cuatri.modelo

import android.app.Activity
import android.content.SharedPreferences
import android.util.Patterns
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto5cuatri.R.string
import com.google.android.gms.maps.MapView
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern


class validationModel {
    internal var context: Activity? = null
    private var PASSWORD_PATTERN: Pattern =
        Pattern.compile("^(?=.{6,15}\$).*")//expresion regular para min y max de contraseña
    internal var preferencias_user:SharedPreferences? = null
    internal var preferencias_permiso:SharedPreferences? = null
    internal  var preferencia_color:SharedPreferences?=null
    internal var PREF_USUARIO = "usuario"
    internal var PREF_PERMISO ="permiso"
    internal  var PREF_COLOR="color"
    internal val INTERVALO = 2000 //2 segundos para salir
    internal var tiempoPrimerClick: Long = 0

    internal fun Validate(
        editText: EditText,
        textInputLayout: TextInputLayout,
        inputType: String,
        errorMensaje: Int
    ): Boolean {
        if (editText.text.toString().trim().isEmpty()) {
            textInputLayout.error = context?.getString(string.valid_campo)
            return false
        } else {
            if (inputType == "email") {
                if (!Patterns.EMAIL_ADDRESS.matcher(editText.text.toString()).matches()) {
                    textInputLayout.error = context?.getString(errorMensaje)
                    return false
                }
            }
            if (inputType == "pass") {
                if (!PASSWORD_PATTERN.matcher(editText.text.toString()).matches()) {
                    textInputLayout.error = context?.getString(errorMensaje)
                    return false
                }
            }
            textInputLayout.error = null
        }
        return true
    }

    internal fun ValidateRegistro(editText: EditText, inputType: String, errorMensaje: Int): Boolean {
        if (editText.text.toString().trim().isEmpty()) {
            editText.error = context?.getString(string.valid_campo)
            return false
        } else {
            if (inputType == "email") {
                if (!Patterns.EMAIL_ADDRESS.matcher(editText.text.toString()).matches()) {
                    editText.error = context?.getString(errorMensaje)
                    return false
                }
            }
            if (inputType == "pass") {
                if (!PASSWORD_PATTERN.matcher(editText.text.toString()).matches()) {
                    editText.error = context?.getString(errorMensaje)
                    return false
                }
            }
            editText.error = null
        }
        return true
    }

    internal fun showButton(btn: Button) {
        btn.visibility = View.VISIBLE
    }

    internal fun hideButton(btn: Button) {
        btn.visibility = View.INVISIBLE
    }

    internal  fun hideProgress(progress: ProgressBar) {
        progress.visibility = View.INVISIBLE
    }

    internal fun showProgress(progress: ProgressBar) {
        progress.visibility = View.VISIBLE
    }

    internal  fun mensaje(mensajeError: Int) {
        Toast.makeText(context?.applicationContext, context?.getString(mensajeError), Toast.LENGTH_SHORT
        ).show()
    }
    internal  fun showMap (mapView: MapView, recyclerView: RecyclerView){
        mapView.isVisible=true
        recyclerView.isVisible=false
    }
    internal fun hideMap (mapView: MapView, recyclerView: RecyclerView){
        mapView.isVisible=false
        recyclerView.isVisible=true
    }

    internal fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0f, // fromXDelta
            0f, // toXDelta
            view.height.toFloat(), // fromYDelta
            0f
        )                // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
        view.bringToFront()
    }

    // slide the view from its current position to below itself
    internal fun slideDown(view: View) {
        val animate = TranslateAnimation(
            0f, // fromXDelta
            0f, // toXDelta
            0f, // fromYDelta
            view.height.toFloat()
        ) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)



    }
}