package com.example.proyecto5cuatri.activitys
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.modelo.permisoModel
import kotlinx.android.synthetic.main.activity_permisos.*

class permisos : AppCompatActivity(), View.OnClickListener {

    private val permisos = permisoModel(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permisos)
        permisos.validarPermisoAcvityPermiso()
        btn_allow_permisos.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (btn_allow_permisos ==v){
            permisos.permisosValidacion()
        }
    }



}
