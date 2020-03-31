package com.example.proyecto5cuatri.modelo

class publicacionModel( id:Int=0,
    titulo:String="", descripcion:String="", categoria:String="", fecha:String="", tarifa:String="", extra:String="", disponibilidad:String="", latitud:Double=0.0,
    longitud:Double=0.0,
    empleada:String="", icono:String="", radio:Double=0.0, visible:Boolean =false, fotoEmpleada:String="",telefono:String="",score:Double=0.0
) {
    internal var id=id
    internal var titulo = titulo
    internal var descripcion = descripcion
    internal var fecha = fecha
    internal var tarifa = tarifa
    internal var extra = extra
    internal var disponibilidad = disponibilidad
    internal var latitud = latitud
    internal var longitud = longitud
    internal var empleada = empleada
    internal var icono = icono
    internal var categoria = categoria
    internal var radio = radio
    internal var visible = visible
    internal var fotoEmpleada=fotoEmpleada
    internal var score=score
    internal var telefono=telefono

}