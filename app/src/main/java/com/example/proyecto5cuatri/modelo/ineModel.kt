package com.example.proyecto5cuatri.modelo

import android.content.Context
import android.graphics.Bitmap
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextRecognizer

class ineModel(context: Context) {
    private var context = context
    internal var nombre = ""
    internal var curp = ""
    internal var nacimiento = ""
    internal var direccion = ""
    internal fun scanINE(bitmap: Bitmap){
        val text = TextRecognizer.Builder(context).build()
        if(text.isOperational){
            val frame = Frame.Builder().setBitmap(bitmap).build()
            val items = text.detect(frame)
            val sb = StringBuilder()
            for (i in 0 until items.size()) {
                val myItem = items.valueAt(i)
                sb.append(myItem.value)
                sb.append(" ")
            }
            if (sb.toString() != "") {
                var OCR = sb.toString()
                OCR = OCR.replace("\n".toRegex(), " ")
                OCR = OCR.replace("\\+".toRegex(), "")
                OCR = OCR.replace("-".toRegex(), "")
                val cadena =OCR.split(" ").toTypedArray()
                nombre = getNombre(cadena)
                curp = getCurp(cadena)
                nacimiento = getNacimiento(cadena)
                direccion = getDomicilio(cadena)

            }else{
            }
        }else{
        }
    }

    private fun getNombre(cadena: Array<String>): String {
        try {
            var start = false
            var setSting = ""
            var contar = 1
            var nombres = ""
            for (i in cadena.indices) {
                if (cadena[i].toLowerCase() == "nombre") {
                    start = true
                }else if ((cadena[i].toLowerCase() =="domicili0" || cadena[i].toLowerCase() == "domicilio0" || cadena[i].toLowerCase() == "folio" ||
                            cadena[i].toLowerCase() == "clave" || cadena[i].toLowerCase() == "elector" ||
                            cadena[i].toLowerCase() == "curp" || cadena[i].toLowerCase() == "fecha" ||
                            cadena[i].toLowerCase() == "sexo" || cadena[i].toLowerCase() == "estado") && start
                ) {
                    return setSting.trim { it <= ' ' }
                } else {

                }
                if (start) {
                    if (cadena[i].toLowerCase() == "nombre") {

                    } else {
                        setSting = setSting + " " + cadena[i].toUpperCase()
                        if (contar == 1) {
                            contar += 1

                        } else if (contar == 2) {

                            contar += 1
                        } else if (contar == 3) {
                            nombres = nombres + " " + cadena[i].toUpperCase()
                        }
                    }
                } else {

                }
            }
            return setSting.trim { it <= ' ' }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ""
        }
    }

    private fun getCurp(cadena: Array<String>): String {
        try {
            var start = false
            var setSting = ""
            for (i in cadena.indices) {
                if (cadena[i].toLowerCase() == "curp") {
                    start = true
                } else {
                }
                if (start) {
                    if (cadena[i].toLowerCase() == "curp") {

                    } else {
                        setSting = cadena[i].toUpperCase()
                        start = false
                        return setSting.trim { it <= ' ' }
                    }
                } else {

                }
            }
            return setSting.trim { it <= ' ' }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ""
        }

    }

    private fun getNacimiento(cadena: Array<String>): String {
        try {
            var start = false
            var setSting = ""
            for (i in cadena.indices) {
                if (cadena[i].toLowerCase() == "nacimiento" || cadena[i].toLowerCase() == "nacimento") {
                    start = true
                } else {

                }
                if (start) {
                    if (cadena[i].toLowerCase() == "nacimiento" || cadena[i].toLowerCase() == "nacimento") {

                    } else {
                        setSting = cadena[i].toUpperCase()
                        start = false
                        return setSting.trim { it <= ' ' }
                    }
                } else {

                }
            }
            return setSting.trim { it <= ' ' }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ""
        }

    }

    private fun getDomicilio(cadena: Array<String>): String {
        try {
            var start = false
            var setSting = ""
            for (i in cadena.indices) {
                if (cadena[i].toLowerCase() == "domicilio0" || cadena[i].toLowerCase() =="domicili0") {
                    start = true
                } else if (cadena[i].toLowerCase() == "clave" || cadena[i].toLowerCase() == "folio") {
                    start = false
                } else {
                }
                if (start) {
                    if (cadena[i].toLowerCase() == "domicilio") {
                    } else {
                        setSting = setSting + " " + cadena[i].toUpperCase()
                    }
                } else {

                }
            }
            return setSting.trim { it <= ' ' }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ""
        }

    }
}