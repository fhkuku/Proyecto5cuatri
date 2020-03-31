package com.example.proyecto5cuatri.activitys.ui
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.activitys.info_publicacion
import com.example.proyecto5cuatri.adapter.adpInteres
import com.example.proyecto5cuatri.adapter.adpPublicaciones
import com.example.proyecto5cuatri.adapter.adp_mapa_publicacion
import com.example.proyecto5cuatri.interfaces.ApiService
import com.example.proyecto5cuatri.modelo.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : Fragment(), OnMapReadyCallback, View.OnClickListener {


    private var marker:Marker? = null
    private var mapaModel:mapaModel?=null
    private var adpMapa:adp_mapa_publicacion? = null
    private var start = startApi()
    private var locationManager:LocationManager? =null
    private var bm:Bitmap?=null
    private var validacion= validationModel()
    private var idinteres :String=""
    private var circle:Circle?= null
    private var ngoogle:GoogleMap?=null
    private var root:View? = null
    private var markers = ArrayList<Marker>()
    private var Circles = ArrayList<Circle>()
    private var listllenarPublicacion  = ArrayList<publicacionModel>()
    var listaPublicacion:ArrayList<publicacionModel>?=null
    private lateinit var service: ApiService
    private var interes = interesModel()
    var criteria:Criteria? =null
    var provider:String? = null
    var mylocation:Location? = null
    var isCheck = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)
        mapaModel = mapaModel(root!!.context)
        root!!.sCambio.text="Lista"
        validacion.preferencias_user = root!!.context.getSharedPreferences(validacion.PREF_USUARIO, Context.MODE_PRIVATE)
        validacion.preferencia_color=root!!.context.getSharedPreferences(validacion.PREF_COLOR, Context.MODE_PRIVATE)
        idinteres = validacion.preferencias_user!!.getString("idinteres","").toString()
        getCategorias()
        if (root!!.mapPublicacion != null) {
            root!!.mapPublicacion.onCreate(null)
            root!!.mapPublicacion.onResume()
            root!!.mapPublicacion.getMapAsync(this)
        }
        root!!.sCambio.setOnClickListener(this)
        LocalBroadcastManager.getInstance(root!!.context)
            .registerReceiver(mensajeRecibido, IntentFilter("mensaje-id"))
        return root
    }

    override fun onClick(v: View?) {
        if (v == root!!.sCambio) {
            if (isCheck) {
                validacion.slideDown(root!!.mapPublicacion)
                root!!.sCambio.text = "Mapa"
                Handler().postDelayed({
                    run {
                        root!!.rcvPublicacion.bringToFront()
                    }
                }, 500)
            } else {
                validacion.slideUp(root!!.mapPublicacion)
                root!!.sCambio.text = "Lista"

            }
            isCheck = !isCheck
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = start.initApi()

        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        criteria = Criteria()
        provider = locationManager?.getBestProvider(criteria, false)
        validarGPS()
    }

    private fun validarGPS(){
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)){
             mapaModel?.gpsNotEnabled(locationManager!!)
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        try {
            ngoogle = googleMap
            mapaModel?.estiloMapa(googleMap)
            googleMap!!.isMyLocationEnabled = true
            googleMap!!.uiSettings.isMyLocationButtonEnabled = true
            getPublicaciones(idinteres,googleMap)
            googleMap.setOnInfoWindowClickListener{
                val infowindow = it.tag as publicacionModel
                val intent = Intent(context, info_publicacion::class.java)
                intent.putExtra("id", infowindow.id)
                intent.putExtra("empleada", infowindow.empleada)
                intent.putExtra("descripcion", infowindow.descripcion)
                intent.putExtra("tarifa", infowindow.tarifa)
                intent.putExtra("disponibilidad", infowindow.disponibilidad)
                intent.putExtra("titulo", infowindow.titulo)
                intent.putExtra("fecha", infowindow.fecha)
                intent.putExtra("extra", infowindow.extra)
                intent.putExtra("latitud", infowindow.latitud)
                intent.putExtra("longitud", infowindow.longitud)
                intent.putExtra("radio", infowindow.radio)
                intent.putExtra("icono", infowindow.icono)
                intent.putExtra("categoria",infowindow.categoria)
                intent.putExtra("fotoEmpleada",infowindow.fotoEmpleada)
                intent.putExtra("telefono",infowindow.telefono)
                intent.putExtra("score",infowindow.score)
                intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
                context?.startActivity(intent)
            }

        }catch (e:Exception){
        }
    }
    var mensajeRecibido: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val idinteres = intent.getStringExtra("id").toString()
            var share = validacion.preferencia_color!!.edit()
            share.putString("color",idinteres).apply()
            share.commit()
            getPublicaciones(idinteres,ngoogle)

        }
    }
    private val locationListenerNetwork = object : LocationListener {
        @SuppressLint("MissingPermission")
        override fun onLocationChanged(location: Location) {
            if(mylocation!=null){}else{
                mylocation = locationManager?.getLastKnownLocation(provider)
                val yo = LatLng(mylocation!!.latitude, mylocation!!.longitude )
                ngoogle!!.moveCamera(CameraUpdateFactory.newLatLngZoom(yo, 14F))
            }
            val distancia = FloatArray(2)
                listaPublicacion = ArrayList()
                for (i in 0 until markers.size) {
                    Location.distanceBetween(
                        location.latitude,
                        location.longitude,
                        markers[i].position.latitude,
                        markers[i].position.longitude,
                        distancia
                    )
                    var boolean = distancia[0] <= Circles[i].radius
                    listaPublicacion!!.add(publicacionModel(listllenarPublicacion[i].id, listllenarPublicacion[i].titulo,listllenarPublicacion[i].descripcion, listllenarPublicacion[i].fecha, listllenarPublicacion[i].tarifa, listllenarPublicacion[i].extra,listllenarPublicacion[i].disponibilidad, listllenarPublicacion[i].categoria,listllenarPublicacion[i].latitud, listllenarPublicacion[i].longitud, listllenarPublicacion[i].empleada, listllenarPublicacion[i].icono, listllenarPublicacion[i].radio, boolean,listllenarPublicacion[i].fotoEmpleada,listllenarPublicacion[i].telefono, listllenarPublicacion[i].score))
                    markers[i].isVisible = boolean
                    Circles[i].isVisible = boolean
                }
            val rcv = root!!.rcvPublicacion
            val adp = adpPublicaciones(root!!.context.applicationContext, listaPublicacion!!)
            rcv.layoutManager = GridLayoutManager(root!!.context.applicationContext, 1)
            rcv?.adapter = adp

        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        }

        override fun onProviderEnabled(provider: String) {
        }

        override fun onProviderDisabled(provider: String) {
        }
    }


    private fun hideMarker(marker: ArrayList<Marker>, circle: ArrayList<Circle>){
        for (i in 0 until marker.size) {
            markers[i].isVisible = false
            circle[i].isVisible = false
        }
    }

    private fun getPublicaciones(id:String, googleMap: GoogleMap?) {
            googleMap?.clear()
            service.GetPublicacion(id).enqueue(object : Callback<JsonArray> {
                @SuppressLint("MissingPermission")
                override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                    listllenarPublicacion = ArrayList()
                    val json = Gson().toJson(response?.body())
                    val array = JSONArray(json)
                    markers = ArrayList()
                    Circles = ArrayList()
                    for (i in 0 until array.length()) {
                        val row = array.getJSONObject(i)
                        val publicacionmodel = publicacionModel()
                        publicacionmodel.id = row.getString("idpu").toInt()
                        publicacionmodel.titulo = row.getString("Titulo").trim()
                        publicacionmodel.disponibilidad = row.getString("dispo").trim()
                        publicacionmodel.latitud = row.getString("lat").toDouble()
                        publicacionmodel.longitud = row.getString("longi").toDouble()
                        publicacionmodel.icono = row.getString("icono")
                        publicacionmodel.radio = row.getString("radio").toDouble()
                        publicacionmodel.empleada = row.getString("empleada")
                        publicacionmodel.descripcion = row.getString("descripcion")
                        publicacionmodel.tarifa = row.getString("tarifa")
                        publicacionmodel.extra = row.getString("extra")
                        publicacionmodel.categoria = row.getString("categoria")
                        publicacionmodel.fotoEmpleada= row.getString("fotoEmpleada")
                        publicacionmodel.score=row.getString("score").toDouble()
                        publicacionmodel.telefono = row.getString("telefono")
                        listllenarPublicacion.add(publicacionModel(publicacionmodel.id,publicacionmodel.titulo,publicacionmodel.descripcion, publicacionmodel.fecha, publicacionmodel.tarifa,publicacionmodel.extra, publicacionmodel.disponibilidad, publicacionmodel.categoria, publicacionmodel.latitud, publicacionmodel.longitud, publicacionmodel.empleada, publicacionmodel.icono, publicacionmodel.radio,false, publicacionmodel.fotoEmpleada,publicacionmodel.telefono,publicacionmodel.score))
                        val d = Base64.decode(publicacionmodel.icono, Base64.DEFAULT)
                        bm = BitmapFactory.decodeByteArray(d, 0, d.size)
                        adpMapa = adp_mapa_publicacion(activity)
                        ngoogle?.setInfoWindowAdapter(adpMapa)
                        marker = ngoogle?.addMarker(
                                MarkerOptions().position(
                                    LatLng(
                                        publicacionmodel.latitud,
                                        publicacionmodel.longitud
                                    )
                                ).title(publicacionmodel.titulo)
                                    .icon(BitmapDescriptorFactory.fromBitmap(bm))
                            )
                            circle = ngoogle?.addCircle(
                                CircleOptions().center(
                                    LatLng(
                                        publicacionmodel.latitud,
                                        publicacionmodel.longitud
                                    )
                                ).radius(publicacionmodel.radio).strokeWidth(4F).strokeColor(Color.BLUE)
                            )
                        marker!!.isVisible =false
                        circle!!.isVisible=false
                        markers.add(marker!!)
                        Circles.add(circle!!)
                        marker?.tag = publicacionmodel
                    }


                    if (null != locationManager?.getProvider(/*provider*/ LocationManager.GPS_PROVIDER)){
                        locationManager?.requestLocationUpdates(/*provider*/LocationManager.GPS_PROVIDER, 20*100, 10F, locationListenerNetwork)
                    }
                    if (null != locationManager?.getProvider(LocationManager.NETWORK_PROVIDER)){
                        locationManager?.requestLocationUpdates(/*provider*/LocationManager.GPS_PROVIDER, 20*100, 10F, locationListenerNetwork)
                    }
                }
                override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                    Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun getCategorias() {
        service.getCategorias().enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                var rcv: RecyclerView? = null
                var adp: adpInteres? = null
                val lista = ArrayList<interesModel>()
                val json = Gson().toJson(response?.body())
                val array = JSONArray(json)
                for (i in 0 until array.length()) {
                    val row = array.getJSONObject(i)
                    interes.id = row.getString("id").toString()
                    interes.nombre = row.getString("nombre").toString()
                    interes.icono = row.getString("icono")
                    lista.add(interesModel(interes.id, interes.nombre, interes.icono))
                }
                rcv = root!!.rcvInteres
                adp = adpInteres(root!!.context.applicationContext, lista, 0)
                rcv?.layoutManager = LinearLayoutManager(root!!.context.applicationContext, LinearLayoutManager.HORIZONTAL, false)
                rcv?.adapter = adp
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
            }
        })
    }


    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        root!!.mapPublicacion.onResume()
        validarGPS()
        locationManager?.requestLocationUpdates(provider, 20*100,10F, locationListenerNetwork)
    }
    override fun onDestroy() {
        super.onDestroy()
        root!!.mapPublicacion.onDestroy()
    }
    override fun onPause() {
        super.onPause()
        root!!.mapPublicacion.onPause()
    }

}