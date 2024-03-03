package com.daedongyeojido.daedongyeojido_v1

import com.daedongyeojido.daedongyeojido_v1.auth.LoginRequest
import com.daedongyeojido.daedongyeojido_v1.auth.LoginResponse
import com.daedongyeojido.daedongyeojido_v1.club.ClubDetailResponse
import com.daedongyeojido.daedongyeojido_v1.club.NoticeData
import com.daedongyeojido.daedongyeojido_v1.home.HomeClubResponse
import com.daedongyeojido.daedongyeojido_v1.user.MypageResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ServerApi {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
    @GET("main")
    fun getMain(): Call<HomeClubResponse>

    @GET("user/my-info")
    fun getMyInfo(@Header("Authorization") token: String): Call<MypageResponse>

    @GET("club/info/{clubName}")
    fun getClubInfo(@Path("clubName") clubName: String): Call<ClubDetailResponse>

    @GET("notice/{clubName}")
    fun getNotice(
        @Path("clubName") clubName: String,
        @Header("Authorization") token: String
    ): Call<List<NoticeData>>
}