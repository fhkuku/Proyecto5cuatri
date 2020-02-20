package com.example.proyecto5cuatri.activitys

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.modelo.usuarioModel
import com.example.proyecto5cuatri.modelo.validationModel
import kotlinx.android.synthetic.main.activity_registro.*

class registro : AppCompatActivity() {

    val usuario = usuarioModel()
    val validacion = validationModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        validacion.context = this

        btnRegistrarse.setOnClickListener {
            validacion.showProgress(bar_registro)
            validacion.hideButton(btnRegistrarse)
            if (!validateForm()) {
                validacion.showButton(btnRegistrarse)
                validacion.hideProgress(bar_registro)
            } else {
                if (txtpass.text.toString() == txtConfirmar.text.toString()) {
                    if (rgp_sexo.checkedRadioButtonId != null) {
                        val radio: RadioButton = findViewById(rgp_sexo.checkedRadioButtonId)
                        val intento = Intent(applicationContext, registrointeres::class.java)
                        intento.putExtra("nombre", txtNombre.text.toString())
                        intento.putExtra("apellido", txtApellido.text.toString())
                        intento.putExtra("email", txtCorreo.text.toString())
                        intento.putExtra("pass", txtpass.text.toString())
                        intento.putExtra("sexo", radio.text.toString())
                        intento.putExtra("telefono", txttelefono.text.toString())
                        startActivity(intento)
                        validacion.showButton(btnRegistrarse)
                    } else {
                        validacion.mensaje(R.string.valid_rbt)
                        validacion.showButton(btnRegistrarse)
                        validacion.hideProgress(bar_registro)
                    }
                } else {
                    validacion.mensaje(R.string.valid_passmacht)
                    validacion.showButton(btnRegistrarse)
                    validacion.hideProgress(bar_registro)
                }
            }
        }
    }


    private fun validateForm(): Boolean {
        if (!validacion.ValidateRegistro(txtNombre, "", R.string.valid_campo)) {
            return false
        }
        if (!validacion.ValidateRegistro(txtApellido, "", R.string.valid_campo)) {
            return false
        }
        if (!validacion.ValidateRegistro(txtCorreo, "email", R.string.valid_email)) {
            return false
        }
        if (!validacion.ValidateRegistro(txtpass, "pass", R.string.valid_pass)) {
            return false
        }
        if (!validacion.ValidateRegistro(txtConfirmar, "pass", R.string.valid_pass)) {
            return false
        }
        return true
    }

}
