package com.example.proyecto5cuatri.activitys.ui
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyecto5cuatri.R
import com.example.proyecto5cuatri.interfaces.ApiService
import com.example.proyecto5cuatri.modelo.fotoModel
import com.example.proyecto5cuatri.modelo.ineModel
import com.example.proyecto5cuatri.modelo.startApi
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_documentos.view.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


open class documentos : Fragment(), View.OnClickListener {

    private var root:View? =null
    private var fotoData:fotoModel?=null
    private var start = startApi()
    private lateinit var service: ApiService
    private var INE:ineModel? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_documentos, container, false)
        fotoData = fotoModel(root?.context!!)
        INE = ineModel(root?.context!!)
        root?.btnCamera?.setOnClickListener(this)
        root?.btn_Add?.setOnClickListener(this)
        return root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = start.initApi()
    }

    override fun onClick(v: View?) {
        if (root!!.btnCamera == v){
            fotoData?.TakePicture()
        }
        if (root!!.btn_Add == v){
            val bitmap = fotoData?.reducirResolucion(fotoData?.uri.toString(),850, 1100)
            val uriImage = fotoData?.reducirPeso(bitmap)
            val imagen = fotoData?.getImagenURL(uriImage)
            val file = File(imagen)
            val filePart = fotoData?.multiPart(file)
            uploadImage(filePart!!)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (resultCode== RESULT_OK){
                if (requestCode==29) {
                    val bitmap = BitmapFactory.decodeFile(fotoData?.path)
                    root?.img_INE?.setImageBitmap(bitmap)
                    INE?.scanINE(bitmap)
                    root?.txtNombre?.text = INE?.nombre
                    root?.txtCurp?.text = INE?.curp
                    root?.txtDomicilio?.text = INE?.direccion
                }
            }
        }catch (e: Exception){}
    }

    fun uploadImage(file: MultipartBody.Part){
        service.loadImage(file).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val json = Gson().toJson(response?.body())
                Toast.makeText(context, json.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}
