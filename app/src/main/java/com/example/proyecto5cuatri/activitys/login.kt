package com.example.proyecto5cuatri.activitys
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.interfaces.ApiService
import com.example.proyecto5cuatri.modelo.permisoModel
import com.example.proyecto5cuatri.modelo.startApi
import com.example.proyecto5cuatri.modelo.usuarioModel
import com.example.proyecto5cuatri.modelo.validationModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Response

class login : AppCompatActivity() {
    val validacion = validationModel()
    val start = startApi()
    val usuario = usuarioModel()
    val permisos = permisoModel(this)
    lateinit var service: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        validacion.context = this
        Permisos()
        validacion.preferencias = getSharedPreferences(validacion.PREF_USUARIO, Context.MODE_PRIVATE)
        val retrofit = start.initApi()
        service = retrofit.create<ApiService>(ApiService::class.java)
        btnLogin.setOnClickListener {
            validacion.showProgress(bar_login)
            validacion.hideButton(btnLogin)
            if (!validateForm()) {
                validacion.showButton(btnLogin)
                validacion.hideProgress(bar_login)
            } else {
                usuario.email = txtEmail.text.toString()
                usuario.pass = txtPassword.text.toString()
                Login(usuario, validacion.preferencias!!)
            }
        }
    }

    fun Login(usuarioModel: usuarioModel, preferencias: SharedPreferences) {
        service.login(usuarioModel.email, usuarioModel.pass)
            .enqueue(object : retrofit2.Callback<JsonArray> {
                @RequiresApi(Build.VERSION_CODES.KITKAT)
                override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                    val json = Gson().toJson(response?.body())
                    val share = preferencias.edit()
                    val array = JSONArray(json)
                    for (i in 0 until array.length()) {
                        val row = array.getJSONObject(i)
                        share.putString("id", row.getString("id"))
                        share.putInt("idperfil", 3)
                        share.putString("nombre", row.getString("nombre"))
                        share.putString("apellido", row.getString("apellido"))
                        share.putString("telefono", row.getString("telefono"))
                        share.putString("sexo", row.getString("sexo"))
                        share.putString("curp", row.getString("curp"))
                        share.putString("fechanacimiento", row.getString("fechanacimiento"))
                        share.putString("longi", row.getString("longi"))
                        share.putString("lat", row.getString("lat"))
                        share.putString("idinteres", row.getString("idinteres"))
                        share.putString("fotoperfil", row.getString("fotoperfil"))
                        share.putString("email", usuarioModel.email)
                        share.putString("pass", usuarioModel.pass)
                        share.commit()
                    }
                    val intento = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intento)
                    finish()
                    validacion.showButton(btnLogin)
                }

                override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "EL suario no exite o no es el perfil correcto.",
                        Toast.LENGTH_SHORT
                    ).show()
                    validacion.hideProgress(bar_login)
                    validacion.showButton(btnLogin)


                }
            })
    }

    fun Registrarse(v: View) {
        try {
            val intent = Intent(applicationContext, registro::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    private fun Permisos(){
        val permisoUbicacion =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permisoUbicacion == PackageManager.PERMISSION_GRANTED) {
        } else {
            permisos.permisosMensaje()
        }
    }

}
