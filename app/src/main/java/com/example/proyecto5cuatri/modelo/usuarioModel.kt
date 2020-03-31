package com.example.proyecto5cuatri.modelo

class usuarioModel(
    email: String = "", pass: String = "",
    status: Int = 0, idperfil: Int = 0,
    nombre: String = "", apellido: String = "",
    telefono: String = "", sexo: String = "",
    curp: String = "", fechanacimiento: String = "",
    fotoperfil: String = "", longi: String = "",
    lat: String = "", idinteres: Int = 0
) {
    internal var email = email
    internal var pass = pass
    internal var status = status
    internal var idperfil = idperfil
    internal var nombre = nombre
    internal var apellido = apellido
    internal var telefono = telefono
    internal var sexo = sexo
    internal var curp = curp
    internal var fechanacimiento = fechanacimiento
    internal  var fotoperfil = fotoperfil
    internal var longi = longi
    internal var lat = lat
    internal var idinteres = idinteres

}