package com.daedongyeojido.daedongyeojido_v1

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {
    private var instance: Retrofit? = null
    private val BASE_URL = "https://prod-server.xquare.app/jung-ho/"

    fun getInstance(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }
}