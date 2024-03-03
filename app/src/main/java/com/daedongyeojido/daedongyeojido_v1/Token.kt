package com.daedongyeojido.daedongyeojido_v1

import android.util.Log

class Token {
    private var accessToken: String = ""
    private var refreshToken: String = ""
    private var part: String = ""

    fun getAccessToken(): String {
        Log.d("token", "getToken")
        return accessToken
    }
    fun setAccessToken(token: String) {
        Log.d("token", "setToken")
        this.accessToken = token
    }

    fun getPart(): String {
        return part
    }
    fun setPart(part: String) {
        this.part = part
    }
}