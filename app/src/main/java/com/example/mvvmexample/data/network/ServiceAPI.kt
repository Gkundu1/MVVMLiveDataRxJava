package com.example.mvvmexample.data.network

import com.example.mvvmexample.data.response.DMSLoginResponse
import com.example.mvvmexample.data.response.MVVMLoginResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ServiceAPI
{
    @FormUrlEncoded
    @POST("login")
    fun userLogin(@Field("email") email:String,
                  @Field("password")password:String):Observable<MVVMLoginResponse>


    @POST("api/v1/user/login")
    fun doLogin(): Observable<DMSLoginResponse>

}