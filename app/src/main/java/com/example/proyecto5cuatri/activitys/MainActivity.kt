package com.example.proyecto5cuatri.activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.modelo.validationModel
import com.example.proyecto5cuatri.ui.home.HomeFragment
import com.example.proyecto5cuatri.ui.perfilFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var validacion = validationModel()
    private var drawerLayout: DrawerLayout? = null
    private var navView: NavigationView? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navView!!.setNavigationItemSelectedListener(this)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow,
                R.id.nav_tools,
                R.id.nav_close_session
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
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_LONG).show()
        }
        validacion.tiempoPrimerClick = System.currentTimeMillis()
    }
    override fun onNavigationItemSelected(item: MenuItem):Boolean{
        when(item.itemId){
            R.id.nav_home ->{

                LoadFragment(HomeFragment())
            }
            R.id.nav_perfil->{
                LoadFragment(perfilFragment())
            }
            R.id.nav_close_session -> {

            }
        }
        drawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }


    fun LoadFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction =  fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment).addToBackStack(null)
        fragmentTransaction.commit()
    }


}
