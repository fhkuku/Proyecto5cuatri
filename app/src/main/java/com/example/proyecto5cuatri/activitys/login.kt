package com.example.proyecto5cuatri.activitys
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.interfaces.ApiService
import com.example.proyecto5cuatri.modelo.activityModel
import com.example.proyecto5cuatri.modelo.startApi
import com.example.proyecto5cuatri.modelo.usuarioModel
import com.example.proyecto5cuatri.modelo.validationModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Response

class login : AppCompatActivity(), View.OnClickListener{


    private val validacion = validationModel()
    private val start = startApi()
    private val usuario = usuarioModel()
    private val actividades = activityModel(this)
    private lateinit var service: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        service = start.initApi()
        validacion.hideProgress(bar_login)
        validacion.context = this
        validacion.preferencias_user = getSharedPreferences(validacion.PREF_USUARIO, Context.MODE_PRIVATE)
        validacion.preferencia_color=getSharedPreferences(validacion.PREF_COLOR, Context.MODE_PRIVATE)
        btnLogin.setOnClickListener(this)
        tvRegistrate.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (btnLogin == v){
            validacion.showProgress(bar_login)
            validacion.hideButton(btnLogin)
            if (!validateForm()) {
                validacion.showButton(btnLogin)
                validacion.hideProgress(bar_login)
            } else {
                usuario.email = txtEmail.text.toString()
                usuario.pass = txtPassword.text.toString()
                Login(usuario, validacion.preferencias_user!!, validacion.preferencia_color!!)
            }
        }else if (tvRegistrate ==v){
            actividades.registro()
        }
    }
    private fun Login(usuarioModel: usuarioModel, preferencias: SharedPreferences, pref_color:SharedPreferences) {
        service.login(usuarioModel.email, usuarioModel.pass)
            .enqueue(object : retrofit2.Callback<JsonArray> {
                @RequiresApi(Build.VERSION_CODES.KITKAT)
                override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                    val json = Gson().toJson(response?.body())
                    val share = preferencias.edit()
                    val color = pref_color.edit()
                    val array = JSONArray(json)
                    for (i in 0 until array.length()) {
                        val row = array.getJSONObject(i)
                        share.putString("id", row.getString("id"))
                        share.putInt("idperfil", 3)
                        share.putInt("idpersona", row.getString("idpersona").toInt())
                        share.putString("nombre", row.getString("nombre"))
                        share.putString("apellido", row.getString("apellido"))
                        share.putString("telefono", row.getString("telefono"))
                        share.putString("sexo", row.getString("sexo"))
                        share.putString("curp", row.getString("curp"))
                        share.putString("fechanacimiento", row.getString("fechanacimiento"))
                        share.putString("longi", row.getString("longi"))
                        share.putString("lat", row.getString("lat"))
                        share.putString("idinteres", row.getString("idinteres"))
                        color.putString("color", row.getString("idinteres"))
                        share.putString("fotoperfil", row.getString("fotoperfil"))
                        share.putString("email", usuarioModel.email)
                        share.putString("pass", usuarioModel.pass)
                        share.commit()
                        color.commit()
                    }
                    actividades.permiso()
                    finish()
                    validacion.showButton(btnLogin)
                }

                override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                    actividades.showMensajeToast("EL suario no exite o no es el perfil correcto.")
                    validacion.hideProgress(bar_login)
                    validacion.showButton(btnLogin)
                }
            })
    }
    private fun validateForm(): Boolean {
        if (!validacion.Validate(txtEmail, txtLayoutEmail, "email", R.string.valid_email)) {
            return false
        }
        if (!validacion.Validate(txtPassword, txtLayoutPassword, "pass", R.string.valid_pass)) {
            return false
        }
        return true
    }



}
