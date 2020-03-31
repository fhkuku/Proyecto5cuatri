package com.example.proyecto5cuatri.modelo

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices

class apiClientGoogle(context: Context) {


    var context = context
    private var api:GoogleApiClient?=null
    fun loadClientGoogle(): GoogleApiClient? {
        api =GoogleApiClient.Builder(context.applicationContext).enableAutoManage(context as FragmentActivity
        ) {

        }.addApi(LocationServices.API).build()
        api?.connect()
        return  api
    }


}

