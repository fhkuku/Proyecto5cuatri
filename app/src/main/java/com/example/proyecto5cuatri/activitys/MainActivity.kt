package com.example.proyecto5cuatri.activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.activitys.ui.Home
import com.example.proyecto5cuatri.activitys.ui.Perfil
import com.example.proyecto5cuatri.activitys.ui.Pubicacion
import com.example.proyecto5cuatri.activitys.ui.documentos
import com.example.proyecto5cuatri.modelo.activityModel
import com.example.proyecto5cuatri.modelo.validationModel
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var validacion = validationModel()
    private var drawerLayout: DrawerLayout? = null
    private var navView: NavigationView? =null
    private val actividades = activityModel(this)
    private var view:View ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actividades.validarSesion()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        LoadFragment(Home())
        validacion.preferencias_user = getSharedPreferences(validacion.PREF_USUARIO, Context.MODE_PRIVATE)
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navView!!.setNavigationItemSelectedListener(this)
        val navController = findNavController(R.id.nav_host_fragment)
        view = navView!!.getHeaderView(0)
        Picasso.with(this).load(validacion!!.preferencias_user?.getString("fotoperfil", "").toString()).into(view!!.img_nav_perfil)
        view!!.tv_nav_nombre.text = validacion!!.preferencias_user?.getString("nombre", "").toString()



        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_perfil, R.id.nav_publicacion,
                R.id.nav_historial, R.id.nav_CV, R.id.nav_sesion
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (validacion.tiempoPrimerClick + validacion.INTERVALO > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
            return
        } else {
            actividades.showMensajeToast("Vuelve a presionar para salir")
        }
        validacion.tiempoPrimerClick = System.currentTimeMillis()
    }
    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        when(item.itemId){
            R.id.nav_home->{ LoadFragment(Home())}
            R.id.nav_perfil->{ LoadFragment(Perfil())}
            R.id.nav_publicacion->{ LoadFragment(Pubicacion())}
            R.id.nav_historial->{ LoadFragment(documentos())}
            R.id.nav_sesion->{
                val share=validacion.preferencias_user!!.edit().clear()
                share.commit()
                actividades.login()
                finish()
            }
        }
        drawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }


    private fun LoadFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction =  fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data!=null){
            view!!.img_nav_perfil.setImageURI(data!!.data)
        }
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}
