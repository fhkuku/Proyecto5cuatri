package com.example.proyecto5cuatri.modelo

class publicacionModel(
    categoria: Int = 0,
    descripcion: String = "",
    tarifa: String = "",
    extra: String = "",
    fecha: String = "",
    idusuario: String = "",
    status: Int = 0,
    dispo: String = "",
    lati: Double = 0.0,
    long: Double = 0.0
) {
    var categoria = categoria
    var descripcion = descripcion
    var tarifa = tarifa
    var extra = extra
    var fecha = fecha
    var idusuario = idusuario
    var status = status
    var dispo = dispo
    var api_Key =
        "pk.eyJ1IjoibmFpYnkiLCJhIjoiY2szcXJpbXk5MDRybDNjbXE4aTdncjNzNiJ9.cgAWHSLCncNGAydECoILWg"
    var lati = lati
    var long = long

}