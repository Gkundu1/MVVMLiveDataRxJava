package com.example.mvvmexample.data.network

import com.example.mvvmexample.data.response.LoginResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface ServiceAPI
{
    @FormUrlEncoded
    @POST("login")
    fun userLogin(@Field("email") email:String,
                  @Field("password")password:String):Observable<LoginResponse>


}