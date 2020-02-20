package com.example.proyecto5cuatri.modelo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class startApi {
    fun initApi(): Retrofit {
        val retrofit: Retrofit = Retrofit.Builder()
            //.baseUrl("http://10.0.2.2/webservices/")
            .baseUrl("http://alexander14-001-site1.dtempurl.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}